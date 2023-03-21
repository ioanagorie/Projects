#include <cvirte.h>		
#include <userint.h>
#include "interfata.h"
#include <advanlys.h>
#include <utility.h>
#include <formatio.h>
#include <ansi_c.h>
#include "toolbox.h"


static int panelHandle=0;
int plotHandle=-1;
int plotHandle2=-1; 
static int freqPanel=0;
static int imgPanel;

// Constants
#define SAMPLE_RATE		0
#define NPOINTS			1

//Global variables
int waveInfo[2]; //waveInfo[0] = sampleRate
				 //waveInfo[1] = number of elements
double *waveData = 0;
double* filtr=0;
double *filtredData2 = 0; 

double sampleRate = 0.0;
int npoints = 0;

//minim, maxim de pe grafic
double maxVal = 0.0;
double minVal = 0.0;
int maxIndex = 0;
int minIndex = 0;

double mean = 0.0; //calculam valoarea medie
double median=0.0; //mediana
double dispersia=0.0; //dispesia

double axis[100];
int hist[100]; //histograma
double alpha=0.0;
double* anvelopa=0;

//flaguri
int loaded=0;
int envelopStart=0;

///fereastra 2

int nrpoints = 0; 
double* filtr2=0;  
int inceput2=0,sfarsit2=0;


int main (int argc, char *argv[])
{
	if (InitCVIRTE (0, argv, 0) == 0)
		return -1;	/* out of memory */
	if ((panelHandle = LoadPanel (0, "interfata.uir", PANEL)) < 0)
		return -1;
	if ((freqPanel = LoadPanel (0, "interfata.uir", PANEL_2)) < 0)
		return -1;
	DisplayPanel (panelHandle);
	RunUserInterface ();
	if (panelHandle > 0)
	     DiscardPanel (panelHandle);
	return 0;
}

//functii specifice primului panou : Prelucrare in domeniul timp

int CVICALLBACK onPanel (int panel, int event, void *callbackData,
						 int eventData1, int eventData2)
{
	switch (event)
	{
		case EVENT_GOT_FOCUS:

			break;
		case EVENT_LOST_FOCUS:

			break;
		case EVENT_CLOSE:
             QuitUserInterface(0);
			break;
	}
	return 0;
}

int CVICALLBACK OnLoad (int panel, int control, int event,
						void *callbackData, int eventData1, int eventData2)
{

	switch (event)
	{
		case EVENT_COMMIT:
           //executa script python pentru conversia unui fisierului .wav in .txt
			LaunchExecutable("python main.py");
			
			//astept sa fie generate cele doua fisiere (modificati timpul daca este necesar
			Delay(4);
			
			//incarc informatiile privind rata de esantionare si numarul de valori
			FileToArray("wafeInfo.txt", waveInfo, VAL_INTEGER, 2, 1, VAL_GROUPS_TOGETHER, VAL_GROUPS_AS_COLUMNS, VAL_ASCII);
			sampleRate = waveInfo[SAMPLE_RATE];
			npoints = waveInfo[NPOINTS];
			
			//alocare memorie pentru numarul de puncte
			waveData = (double *) calloc(npoints, sizeof(double));
			filtr = (double *) calloc(npoints, sizeof(double));
			
			//incarcare din fisierul .txt in memorie (vector)
			FileToArray("waveData.txt", waveData, VAL_DOUBLE, npoints, 1, VAL_GROUPS_TOGETHER, VAL_GROUPS_AS_COLUMNS, VAL_ASCII);
			
			//afisare pe grapful din prima pagina
			DeleteGraphPlot(panel,PANEL_GRAPH,plotHandle,VAL_IMMEDIATE_DRAW);
			PlotY(panel, PANEL_GRAPH, waveData, npoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
		
            //calculam valoarea minima si maxima de pe graf si le afisam
			MaxMin1D(waveData,npoints,&maxVal,&maxIndex,&minVal,&minIndex);
			SetCtrlVal(panel,PANEL_MINIM,minVal);
			SetCtrlVal(panel,PANEL_MAXIMUM,maxVal);
			
			//calculam valoarea medie
			Mean(waveData,npoints,&mean);
			SetCtrlVal(panel,PANEL_MEAN,mean);
			
			//calculam mediana
			Median(waveData,npoints,&median);
			SetCtrlVal(panel,PANEL_MEDIAN,mean);
			
			//calculam dispersia
			StdDev(waveData,npoints,&mean,&dispersia);
			SetCtrlVal(panel,PANEL_DISPERSIE,dispersia);	
			
			//calculam zero crossing
			int zero=0;
			for(int i=0;i<npoints-1;++i)
			{
				if( ( (waveData[i]<0) && (waveData[i+1]>0) ) || ( (waveData[i]>0) && (waveData[i+1]<0))  )
				{
					zero++;
				}
			}
			SetCtrlVal(panel, PANEL_ZERO_CROSSING,zero);
			
			//histograma
			int interval=10;
			Histogram(waveData,npoints,minVal-1,maxVal+1,hist,axis,interval);
			DeleteGraphPlot (panel,PANEL_HISTOGRAM, -1, VAL_DELAYED_DRAW);
            PlotXY (panel,PANEL_HISTOGRAM, axis,  hist, interval, VAL_DOUBLE, VAL_SSIZE_T, VAL_VERTICAL_BAR, VAL_EMPTY_SQUARE, VAL_SOLID, 1, VAL_MAGENTA);
			
			//au fost incarcate datele
			loaded=1;
		
			break;
	}
	return 0;
}

double *ordin1_filtration(double alpha)
{
	for(int i=1;i<npoints;++i)
	{
		filtr[i]=alpha*filtr[i-1]+alpha*waveData[i];
	}
	return filtr;
}

double *median_filtration(int n)
{	double *filtru=(double*)malloc(npoints*sizeof(double));
	/*
	int i, j ,k;
	double suma=0.0;

	for(i=0;i<n-1;++i)
	{
		suma=suma+waveData[i];
	}
	for(j=0;j<n-1;++j)
	{
		filtru[j]=suma/n;
	}
	for(k=n;k<npoints;++k)
	{
		suma=suma-waveData[k-n]+waveData[k];
		filtru[k]=suma/n;
	}
	*/
    MedianFilter(waveData, npoints, n, 0, filtru); 
	return filtru;
}

int CVICALLBACK onFiltru (int panel, int control, int event,
						  void *callbackData, int eventData1, int eventData2)
{
	int val,dim;
	double alpha;	
	switch (event)
	{
		case EVENT_COMMIT:
			
			GetCtrlVal(panelHandle,PANEL_FILTRU,&val);		
			if(val==0)
			{
				//dezactivare control alfa
				SetCtrlAttribute(panelHandle,PANEL_ALPHA,ATTR_DIMMED, 1);
				
				//activare control pt dimensiune fereastra
				SetCtrlAttribute(panelHandle,PANEL_DIMFEREASTRA,ATTR_DIMMED, 0);
				
				//obtinere valoare pentru dimesniune fereastra
				GetCtrlVal(panelHandle,PANEL_DIMFEREASTRA,&dim);
				
				//filtrare mediana
				filtr=median_filtration(dim);
				
				//afisare pe graf
				DeleteGraphPlot(panelHandle,PANEL_GRAPH_2,plotHandle,VAL_IMMEDIATE_DRAW);
				PlotY (panelHandle,PANEL_GRAPH_2, filtr, npoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);			
			}
			else
			{
				//dezactivare control pentru dimensiune fereastra
				SetCtrlAttribute(panelHandle,PANEL_DIMFEREASTRA,ATTR_DIMMED, 1);
				
				//activare control alfa
				SetCtrlAttribute(panelHandle,PANEL_ALPHA,ATTR_DIMMED, 0);
				
				//obtinere valoare alfa 
				GetCtrlVal(panelHandle,PANEL_ALPHA,&alpha);
				
				//filtrare de ordin 1
				filtr=ordin1_filtration(alpha);
				
				//afisare pe grafic
				DeleteGraphPlot(panelHandle,PANEL_GRAPH_2,plotHandle,VAL_IMMEDIATE_DRAW);
				PlotY (panelHandle, PANEL_GRAPH_2, filtr, npoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);			
			}
		
			break;
	}
	return 0;
}


int CVICALLBACK OnAplica (int panel, int control, int event,
						  void *callbackData, int eventData1, int eventData2)
{
	switch (event)
	{
		case EVENT_COMMIT:
			if(loaded)
			{
				onFiltru (panelHandle, PANEL_FILTRU, EVENT_COMMIT,0, 0, 0);
			}			
			break;
	}
	return 0;
}


int CVICALLBACK onOtherSecond (int panel, int control, int event,
							   void *callbackData, int eventData1, int eventData2)
{
	int i;
	double *vect;
	double *vect_filtrat;
	double *envelop;
	int inceput=0,sfarsit=0;
	switch (event)
	{
		case EVENT_COMMIT:
			GetCtrlVal(panelHandle,PANEL_START,&inceput);
			GetCtrlVal(panelHandle,PANEL_STOP,&sfarsit);
			
			vect_filtrat=(double*)calloc(npoints/6,sizeof(double));
			vect=(double*)calloc(npoints/6,sizeof(double));
			envelop=(double*)calloc(npoints/6,sizeof(double));
								
			switch(control)
			{
				case PANEL_NEXT:
					switch(event)
					{
						case EVENT_COMMIT:
							if(sfarsit<6)
							{
								++inceput;
								++sfarsit;
								SetCtrlVal(panelHandle,PANEL_STOP,sfarsit);
								SetCtrlVal(panelHandle,PANEL_START,inceput);
								
								if(loaded==1)
								{	
									for(i=0;i<npoints/6;++i)
									{
										vect_filtrat[i]=filtr[inceput*npoints/6+i];
									}
									for(i=0;i<npoints/6;++i)
									{
										vect[i]=waveData[inceput*npoints/6+i];
									}
									 if(envelopStart==1)  
									 {
									for(i=0;i<npoints/6;++i)
									{
										envelop[i]=anvelopa[inceput*npoints/6+i];
									}
									 }
								
									DeleteGraphPlot (panelHandle,PANEL_GRAPH, -1, VAL_IMMEDIATE_DRAW);
									PlotY (panelHandle, PANEL_GRAPH, vect, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
									
									DeleteGraphPlot (panelHandle, PANEL_GRAPH_2, -1, VAL_IMMEDIATE_DRAW);
									PlotY (panelHandle, PANEL_GRAPH_2, vect_filtrat, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
									
									if(envelopStart==1)
										{
										PlotY (panelHandle, PANEL_GRAPH, envelop, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_CYAN);
										}					
								}
							
							}
							break;
					}
					break;
				case PANEL_PREV:
					switch(event)
					{
						case EVENT_COMMIT:
							if(sfarsit>1)
							{
								--sfarsit;
								--inceput;
								SetCtrlVal(panelHandle,PANEL_STOP,sfarsit);
								SetCtrlVal(panelHandle,PANEL_START,inceput);
								
								if(loaded==1)
								{
									for(i=0;i<npoints/6;++i)
									{
									vect_filtrat[i]=filtr[inceput*npoints/6+i];
									}
								
									for(i=0;i<npoints/6;++i)
									{
										vect[i]=waveData[inceput*npoints/6+i];
									}
									if(envelopStart==1)  
									{
									for(i=0;i<npoints/6;++i)
									{
										envelop[i]=anvelopa[inceput*npoints/6+i];
									}
									}
								DeleteGraphPlot (panelHandle,PANEL_GRAPH, -1, VAL_IMMEDIATE_DRAW);
								PlotY (panelHandle,PANEL_GRAPH, vect, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
								
								DeleteGraphPlot (panelHandle, PANEL_GRAPH_2, -1, VAL_IMMEDIATE_DRAW);
								PlotY (panelHandle, PANEL_GRAPH_2, vect_filtrat, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
								
								if(envelopStart==1)
									{
									PlotY (panelHandle, PANEL_GRAPH, envelop, npoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_CYAN);
									}
								}
			
							}
							break;	
					}
					break;
			}
			break;
	}
	return 0;
}



int CVICALLBACK onAnvelopa (int panel, int control, int event,
							void *callbackData, int eventData1, int eventData2)
{
	switch (event)
	{
		case EVENT_COMMIT:
			if(loaded && envelopStart==0)
			{
				LaunchExecutable("python anvelopa.py");
			
				Delay(2);
				
				anvelopa = (double *) calloc(npoints, sizeof(double));   
				envelopStart=1;
				FileToArray("anvelopa2.txt", anvelopa, VAL_DOUBLE, npoints, 1, VAL_GROUPS_TOGETHER, VAL_GROUPS_AS_COLUMNS, VAL_ASCII);
				PlotY(panel,PANEL_GRAPH, anvelopa, npoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_CYAN); 
				break;
			}
	}
	return 0;
}

int CVICALLBACK onSwitchPanel (int panel, int control, int event,
							   void *callbackData, int eventData1, int eventData2)
{
	switch (event)
	{
		case EVENT_COMMIT:
			 	//schimbarea panoului
			if(panel == panelHandle)
			{
				SetCtrlVal(freqPanel,PANEL_2_SWITCHPANEL , 1);
				DisplayPanel(freqPanel);
				HidePanel(panel);
			}
			else
			{		
				SetCtrlVal(panelHandle,PANEL_SWITCHPANEL, 0);
				DisplayPanel(panelHandle);
				HidePanel(panel);
			}
			break;
	}
	return 0;
}

//functii specifice celui de-al doilea panou : Prelucrare in frecventa

int CVICALLBACK OnFrequencyPanel (int panel, int event, void *callbackData,
								  int eventData1, int eventData2)
{
	switch (event)
	{
		case EVENT_GOT_FOCUS:

			break;
		case EVENT_LOST_FOCUS:

			break;
		case EVENT_CLOSE:
			QuitUserInterface(0); 
			break;
	}
	return 0;
}



int CVICALLBACK onFiltrare (int panel, int control, int event,
							void *callbackData, int eventData1, int eventData2)
{   
   //preluare valoare npoints de pe interfata
	GetCtrlVal(freqPanel, PANEL_2_NUMERIC_NPOINTS, &nrpoints);
	double temp2[nrpoints];
	double temp[nrpoints]; 
	double autoSpectrum2[nrpoints/2];
	double autoSpectrum[nrpoints/2]; 
	double convertedSpectrum2[nrpoints/2];
	double convertedSpectrum[nrpoints/2];
	int order;
	int signalType;
	double powerPeak = 0.0;
	double freqPeak = 0.0;  
	double df = 0.0;
	double df2 = 0.0;
	double dt = 1.0/sampleRate; 
	int i;
	char unit[32] = "V";
    int tipFiltru = 0;
	int tipFereastra=0;
	WindowConst winConst;	
	switch (event)
	{
		case EVENT_COMMIT:
			//preluare valoare grad de pe interfata
			GetCtrlVal(freqPanel,PANEL_2_GRAD,&order);
			
			//afisare wavedata pe npoints pe graful de pe pagina 2
			DeleteGraphPlot(freqPanel, PANEL_2_RAWDATA,-1, VAL_IMMEDIATE_DRAW);
			PlotY(freqPanel, PANEL_2_RAWDATA, waveData, nrpoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
			
			//spectru initial
			
			//se preia de pe interfata tipul ferestrei 
			GetCtrlVal(freqPanel, PANEL_2_WINDOW_TYPE, &tipFereastra);
			
			for(i=0; i<nrpoints;i++)
			{
				temp[i] = waveData[i];
			}
			
			//ferestruire
			if(tipFereastra == 0) 
			{
				ScaledWindowEx(temp, nrpoints, HANNING, 0, &winConst); 
			}
			if(tipFereastra == 1) 
			{
				ScaledWindowEx(temp, nrpoints, TRIANGLE, 0, &winConst); 
			}
			AutoPowerSpectrum(temp, nrpoints, dt, autoSpectrum, &df);
			PowerFrequencyEstimate(autoSpectrum, nrpoints/2, -1, winConst, df, 7, &freqPeak, &powerPeak);
	
			//se afiseaza pe interfata valorile determinate pentru Frequency Peak si Power Peak prin functia PowerFrequencyEstimate
			SetCtrlVal( freqPanel, PANEL_2_FREQUENCY_PEAK, sampleRate);
			SetCtrlVal( freqPanel, PANEL_2_POWER_PEAK, powerPeak);
			
			//Functia converteste spectrul de intrare într-un alt format (linear, logarithmic, dB) pentru o reprezentare grafica mai convenabila.
			SpectrumUnitConversion(autoSpectrum, nrpoints/2, SPECTRUM_POWER, SCALING_MODE_LINEAR, DISPLAY_UNIT_VRMS, df, winConst,convertedSpectrum, unit);

			//stergere graph unde se va plota spectrul semnalului
			DeleteGraphPlot (freqPanel, PANEL_2_SPECTRUM, -1, VAL_IMMEDIATE_DRAW);	
			//plotare spectru semnal
			PlotWaveform( freqPanel,PANEL_2_SPECTRUM, convertedSpectrum, nrpoints/2 ,VAL_DOUBLE, 1.0, 0.0, 0.0, df,VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID,  VAL_CONNECTED_POINTS, VAL_DK_CYAN); 
 	
			//alocare memorie vector in care s evor retine valorile semnalului filtrat
			filtredData2 = (double *) calloc(nrpoints, sizeof(double));
			  
 			//preluare tip filtru de pe interfata
			GetCtrlVal(freqPanel, PANEL_2_FILTER_TYPE, &tipFiltru);

			//aplicare filtru dorit de utilizator asupra semnalului initial
			 switch(tipFiltru)
			{
				case 0:
					Bw_HPF(waveData, nrpoints, sampleRate, sampleRate*(0.5)/2.0, order, filtredData2);
					DeleteGraphPlot (freqPanel, PANEL_2_RAW_WINDOW, plotHandle2, VAL_IMMEDIATE_DRAW);
					PlotY(freqPanel,PANEL_2_RAW_WINDOW, filtredData2, nrpoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_BLUE);
					break;
					
				case 1:
					if(order!=5)order=5; 
				    Ch_BPF(waveData, nrpoints, sampleRate, (sampleRate/2.0)*(0.25),(sampleRate/2.0)*(0.5), 0.1, order, filtredData2);
					DeleteGraphPlot (freqPanel, PANEL_2_RAW_WINDOW, plotHandle2, VAL_IMMEDIATE_DRAW);
					PlotY(freqPanel,PANEL_2_RAW_WINDOW, filtredData2, nrpoints, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_BLUE); 
					break;
			}
		
			
			for(i=0; i<nrpoints;i++)
			{
				temp2[i] = filtredData2[i];
			}
			
			//se ferestruieste semnalul filtrat
			 switch(tipFereastra)
			 {
				 case(0):
				ScaledWindowEx(temp2, nrpoints, HANNING, 0, &winConst);
				//HanWin (temp, nrpoints);
				break;
				 case(1):
				ScaledWindowEx(temp2, nrpoints, TRIANGLE, 0, &winConst);
				//TriWin (temp, nrpoints);
				break;
			 }
			AutoPowerSpectrum(temp2, nrpoints, dt, autoSpectrum2, &df2);
			
			PowerFrequencyEstimate(autoSpectrum2, nrpoints/2, -1, winConst, df, 7, &freqPeak, &powerPeak);

			//Functia converteste spectrul de intrare într-un alt format (linear, logarithmic, dB) pentru o reprezentare grafica mai convenabila.
			SpectrumUnitConversion(autoSpectrum2, nrpoints/2, SPECTRUM_POWER, SCALING_MODE_LINEAR, DISPLAY_UNIT_VRMS, df2, winConst, convertedSpectrum2, unit);

			//stergere graph unde se va plota spectrul semnalului
			DeleteGraphPlot (freqPanel, PANEL_2_FILTRED_SPECTRUM, -1, VAL_IMMEDIATE_DRAW);	
			//plotare spectrul semnalului
			PlotWaveform( freqPanel, PANEL_2_FILTRED_SPECTRUM, convertedSpectrum2, nrpoints/2 ,VAL_DOUBLE, 1.0, 0.0, 0.0, df,VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID,  VAL_CONNECTED_POINTS, VAL_DK_YELLOW);
			
			break;
	}
	return 0;
}



int  CVICALLBACK OnOtherSecond2(int panel, int control, int event, void *callbackData, int eventData1, int eventData2){
	int i;		
	double *vect;
	double *vect_filtrat;
	switch (event)
	{
		case EVENT_COMMIT:
			GetCtrlVal(freqPanel,PANEL_2_START_SECUNDE,&inceput2);
			GetCtrlVal(freqPanel,PANEL_2_STOP_SECUNDE,&sfarsit2);
			
			vect=(double*)calloc(nrpoints/6,sizeof(double));
			vect_filtrat=(double*)calloc(nrpoints/6,sizeof(double));
			switch(control)
			{
				case PANEL_2_NEXT:
					switch(event)
					{
						case EVENT_COMMIT:
							if(sfarsit2<6)
							{
								++inceput2;
								++sfarsit2;
								SetCtrlVal(freqPanel,PANEL_2_STOP_SECUNDE,sfarsit2);
								SetCtrlVal(freqPanel,PANEL_2_START_SECUNDE,inceput2);
								
								if(loaded==1)
								{	
								
									for(i=0;i<nrpoints/6;++i)
									{
										vect[i]=waveData[inceput2*nrpoints/6+i];
									}
									 for(i=0;i<nrpoints/6;++i)
									{
										vect_filtrat[i]=filtredData2[inceput2*nrpoints/6+i];
									}
									
								
									DeleteGraphPlot (freqPanel,PANEL_2_RAWDATA , -1, VAL_IMMEDIATE_DRAW);
									PlotY (freqPanel, PANEL_2_RAWDATA , vect, nrpoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
									DeleteGraphPlot (freqPanel,PANEL_2_RAW_WINDOW , -1, VAL_IMMEDIATE_DRAW);
									PlotY (freqPanel, PANEL_2_RAW_WINDOW , vect_filtrat, nrpoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_BLUE);
								
									
				
								}
							
							}
							break;
					}
					break;
				case PANEL_2_PREV:
					switch(event)
					{
						case EVENT_COMMIT:
							if(sfarsit2>1)
							{
								--sfarsit2;
								--inceput2;
								SetCtrlVal(freqPanel,PANEL_2_STOP_SECUNDE,sfarsit2);
								SetCtrlVal(freqPanel,PANEL_2_START_SECUNDE,inceput2);
								
								if(loaded==1)
								{
								
									for(i=0;i<nrpoints/6;++i)
									{
										vect[i]=waveData[inceput2*nrpoints/6+i];
									}
									 for(i=0;i<nrpoints/6;++i)
									{
										vect_filtrat[i]=filtredData2[inceput2*nrpoints/6+i];
									}
									
								DeleteGraphPlot (freqPanel,PANEL_2_RAWDATA, -1, VAL_IMMEDIATE_DRAW);
								PlotY (freqPanel,PANEL_2_RAWDATA, vect, nrpoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_MAGENTA);
								DeleteGraphPlot (freqPanel,PANEL_2_RAW_WINDOW , -1, VAL_IMMEDIATE_DRAW);
								PlotY (freqPanel, PANEL_2_RAW_WINDOW , vect_filtrat, nrpoints/6, VAL_DOUBLE, VAL_THIN_LINE, VAL_EMPTY_SQUARE, VAL_SOLID, VAL_CONNECTED_POINTS, VAL_BLUE);
								
								}
			
							}
							break;	
					}
					break;
			}
			break;
	}
	return 0;
}

int CVICALLBACK OnGenerateImagine (int panel, int control, int event,
								   void *callbackData, int eventData1, int eventData2)
{  int grafic=0;
   GetCtrlVal(freqPanel, PANEL_2_RING_CHOOSE_GRAPH,&grafic);
	switch (event)
	{
		case EVENT_COMMIT:
			switch(grafic)
			{
				case 0:
					GetCtrlDisplayBitmap(freqPanel, PANEL_2_RAWDATA,1,&imgPanel);
					SaveBitmapToJPEGFile(imgPanel, "RawData.jpeg", JPEG_INTERLACE, 100);
					DiscardBitmap(imgPanel); 
				break;
				case 1:
					GetCtrlDisplayBitmap(freqPanel, PANEL_2_SPECTRUM,1,&imgPanel);
					SaveBitmapToJPEGFile(imgPanel, "SpectrumOfRawData.jpeg", JPEG_INTERLACE, 100);
					DiscardBitmap(imgPanel);
				break;
				case 2:
					GetCtrlDisplayBitmap(freqPanel, PANEL_2_RAW_WINDOW,1,&imgPanel);
					SaveBitmapToJPEGFile(imgPanel, "FiltredData.jpeg", JPEG_INTERLACE, 100);
					DiscardBitmap(imgPanel);
				break;
				case 3:
					GetCtrlDisplayBitmap(freqPanel, PANEL_2_FILTRED_SPECTRUM,1,&imgPanel);
					SaveBitmapToJPEGFile(imgPanel, "SpectrumOfFiltredData.jpeg", JPEG_INTERLACE, 100);
					DiscardBitmap(imgPanel);
				break;
			}
			break;
	}
	return 0;
}
