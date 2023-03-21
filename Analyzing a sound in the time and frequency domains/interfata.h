/**************************************************************************/
/* LabWindows/CVI User Interface Resource (UIR) Include File              */
/*                                                                        */
/* WARNING: Do not add to, delete from, or otherwise modify the contents  */
/*          of this include file.                                         */
/**************************************************************************/

#include <userint.h>

#ifdef __cplusplus
    extern "C" {
#endif

     /* Panels and Controls: */

#define  PANEL                            1       /* callback function: onPanel */
#define  PANEL_GRAPH                      2       /* control type: graph, callback function: (none) */
#define  PANEL_GRAPH_2                    3       /* control type: graph, callback function: (none) */
#define  PANEL_HISTOGRAM                  4       /* control type: graph, callback function: (none) */
#define  PANEL_PREV                       5       /* control type: command, callback function: onOtherSecond */
#define  PANEL_START                      6       /* control type: numeric, callback function: (none) */
#define  PANEL_STOP                       7       /* control type: numeric, callback function: (none) */
#define  PANEL_NEXT                       8       /* control type: command, callback function: onOtherSecond */
#define  PANEL_MINIM                      9       /* control type: numeric, callback function: (none) */
#define  PANEL_MEAN                       10      /* control type: numeric, callback function: (none) */
#define  PANEL_MAXIMUM                    11      /* control type: numeric, callback function: (none) */
#define  PANEL_MEDIAN                     12      /* control type: numeric, callback function: (none) */
#define  PANEL_DISPERSIE                  13      /* control type: numeric, callback function: (none) */
#define  PANEL_ZERO_CROSSING              14      /* control type: numeric, callback function: (none) */
#define  PANEL_FILTRU                     15      /* control type: ring, callback function: onFiltru */
#define  PANEL_DIMFEREASTRA               16      /* control type: numeric, callback function: (none) */
#define  PANEL_ALPHA                      17      /* control type: scale, callback function: (none) */
#define  PANEL_APLICA                     18      /* control type: command, callback function: OnAplica */
#define  PANEL_ANVELOPA                   19      /* control type: command, callback function: onAnvelopa */
#define  PANEL_SWITCHPANEL                20      /* control type: binary, callback function: onSwitchPanel */
#define  PANEL_LOAD                       21      /* control type: command, callback function: OnLoad */
#define  PANEL_DECORATION                 22      /* control type: deco, callback function: (none) */

#define  PANEL_2                          2       /* callback function: OnFrequencyPanel */
#define  PANEL_2_SWITCHPANEL              2       /* control type: binary, callback function: onSwitchPanel */
#define  PANEL_2_RAWDATA                  3       /* control type: graph, callback function: (none) */
#define  PANEL_2_RAW_WINDOW               4       /* control type: graph, callback function: (none) */
#define  PANEL_2_SPECTRUM                 5       /* control type: graph, callback function: (none) */
#define  PANEL_2_FILTRED_SPECTRUM         6       /* control type: graph, callback function: (none) */
#define  PANEL_2_WINDOW_TYPE              7       /* control type: ring, callback function: (none) */
#define  PANEL_2_FILTER_TYPE              8       /* control type: ring, callback function: (none) */
#define  PANEL_2_FILTRARE                 9       /* control type: command, callback function: onFiltrare */
#define  PANEL_2_NUMERIC_NPOINTS          10      /* control type: numeric, callback function: (none) */
#define  PANEL_2_POWER_PEAK               11      /* control type: numeric, callback function: (none) */
#define  PANEL_2_FREQUENCY_PEAK           12      /* control type: numeric, callback function: (none) */
#define  PANEL_2_GRAD                     13      /* control type: numeric, callback function: (none) */
#define  PANEL_2_PREV                     14      /* control type: command, callback function: OnOtherSecond2 */
#define  PANEL_2_NEXT                     15      /* control type: command, callback function: OnOtherSecond2 */
#define  PANEL_2_STOP_SECUNDE             16      /* control type: numeric, callback function: (none) */
#define  PANEL_2_START_SECUNDE            17      /* control type: numeric, callback function: (none) */
#define  PANEL_2_GEN_IMAG                 18      /* control type: command, callback function: OnGenerateImagine */
#define  PANEL_2_RING_CHOOSE_GRAPH        19      /* control type: ring, callback function: (none) */
#define  PANEL_2_DECORATION               20      /* control type: deco, callback function: (none) */


     /* Control Arrays: */

          /* (no control arrays in the resource file) */


     /* Menu Bars, Menus, and Menu Items: */

          /* (no menu bars in the resource file) */


     /* Callback Prototypes: */

int  CVICALLBACK onAnvelopa(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK OnAplica(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK onFiltrare(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK onFiltru(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK OnFrequencyPanel(int panel, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK OnGenerateImagine(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK OnLoad(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK onOtherSecond(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK OnOtherSecond2(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK onPanel(int panel, int event, void *callbackData, int eventData1, int eventData2);
int  CVICALLBACK onSwitchPanel(int panel, int control, int event, void *callbackData, int eventData1, int eventData2);


#ifdef __cplusplus
    }
#endif
