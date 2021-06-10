package com.example.tmaptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private final Context acContext = this;

    private int cnt1 = 0;
    private int cnt2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();

        LinearLayout layoutTmap = (LinearLayout)findViewById(R.id.layoutTmap);
        TMapView tMapView = new TMapView(acContext);
        tMapView.setSKTMapApiKey("");   # insert your tmap api key

        TMapMarkerItem st_marker = new TMapMarkerItem();
        TMapPoint st_point = new TMapPoint(37.73213660662052, 127.08287877338195);
//        TMapPoint st_point = new TMapPoint(37.570841, 126.985302);
        layoutTmap.addView(tMapView);

        // Marker Icon
        Bitmap st_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_b_m_s);
        st_marker.setIcon(st_icon);
        st_marker.setPosition(0.5f, 1.0f);
        st_marker.setTMapPoint(st_point);
        st_marker.setName("SKT타워");
        tMapView.addMarkerItem("st_marker", st_marker);

        TMapMarkerItem ed_marker = new TMapMarkerItem();
//        TMapPoint ed_point = new TMapPoint(37.75081284429587, 127.11930768309358);
//        TMapPoint ed_point = new TMapPoint(37.297557588874355, 126.80048350157524);
        TMapPoint ed_point = new TMapPoint(35.32608533432017, 129.02384243289552);
//        TMapPoint ed_point = new TMapPoint(37.579600, 126.976998);

        // Marker Icon
        Bitmap ed_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_b_m_e);
        ed_marker.setIcon(ed_icon);
        ed_marker.setPosition(0.5f, 1.0f);
        ed_marker.setTMapPoint(ed_point);
        ed_marker.setName("경복궁");
        tMapView.addMarkerItem("ed_marker", ed_marker);

//        double center_point_lat = (st_point.getLatitude() + ed_point.getLatitude())/2;
//        double center_point_lon = (st_point.getLongitude() + ed_point.getLongitude())/2;
//
//        tMapView.setCenterPoint(center_point_lon, center_point_lat);

//        TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
//        TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)

        // TMapMarkerItem2
        TMapPoint tpoint = new TMapPoint(37.566413, 126.985003);
        tMapView.setCenterPoint(tpoint.getLongitude(), tpoint.getLatitude());

        MarkerOverlay marker = new MarkerOverlay(acContext, "custom", "marker");
        String strID = "TMapMarkerItem2";

        marker.setPosition(0.5f,1.0f);
        marker.setID(strID);
        marker.setTMapPoint(new TMapPoint(tpoint.getLatitude(), tpoint.getLongitude()));

        tMapView.addMarkerItem2(strID, marker);


        String strData = "창경궁";


        TMapData tMapData = new TMapData();
        Bitmap poiIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_r_m_p);
        Bitmap poiIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_b_m_p);
        ArrayList<TMapPOIItem> poiList = new ArrayList<>();


        /*
        // find POI
        tMapData.findAllPOI(strData,30, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {
                for(int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                    poiList.add(item);

                    Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                            "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                            "Point: " + item.getPOIPoint().toString());
                }

                Log.d("poi", "There are "+poiItem.size()+" places searched");
                Log.d("poi", "make poi markers");

                for (int i=0; i<poiList.size(); i++) {

                    // Multiple markers with TMapMarkerItem
                    TMapMarkerItem poiMarker = new TMapMarkerItem();
                    TMapPOIItem mPoiItem = poiList.get(i);
                    poiMarker.setVisible(TMapMarkerItem.VISIBLE);
                    if (i < 5) {
                        poiMarker.setCanShowCallout(true);
                        poiMarker.setCalloutTitle(mPoiItem.getPOIName());
                        if (mPoiItem.getPOIAddress().endsWith(" null")) {
                            poiMarker.setCalloutSubTitle(mPoiItem.getPOIAddress().substring(0, mPoiItem.getPOIAddress().length() - 5));
                        }
                    }
                    poiMarker.setCalloutLeftImage(st_icon);
                    poiMarker.setCalloutRightButtonImage(ed_icon);
                    poiMarker.setEnableClustering(true);
                    poiMarker.setName(mPoiItem.getPOIName());
                    poiMarker.setIcon(poiIcon1);
                    poiMarker.setTMapPoint(mPoiItem.getPOIPoint());
                    tMapView.addMarkerItem("poiMarker" + i, poiMarker);

                }
                Log.d("poi", "Finish making poi markers");
            }
        });
        */



        new Thread() {
            @Override
            public void run() {

//                // Find Car Path and return TMapPolyLine
//                try {
//                    TMapPolyLine tMapPolyLine = new TMapData().findPathData(st_point, ed_point);
//                    tMapPolyLine.setLineColor(Color.BLUE);
//                    tMapPolyLine.setLineWidth(2);
//                    tMapView.addTMapPolyLine("Line1", tMapPolyLine);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                }


                // Find path of car or pedestrian and return TMapData
                TMapData tmapdata = new TMapData();

                tmapdata.findPathDataAllType(TMapData.TMapPathType.CAR_PATH, st_point, ed_point, new TMapData.FindPathDataAllListenerCallback() {
                    @Override
                    public void onFindPathDataAll(Document document) {
                        String preCoord = st_point.getLongitude()+","+st_point.getLatitude();
                        String curCoord;
                        ArrayList<TMapPoint> coordsList = new ArrayList<>();
                        coordsList.add(st_point);
                        Element root = document.getDocumentElement();
                        NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");

                        for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                            NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
//                            Log.d("debug1", nodeListPlacemark.item(i).getTextContent().trim());
//                            Log.d("iter", ""+i);
                            int n = nodeListPlacemarkItem.getLength();
                            for( int j=0; j<n; j++ ) {
                                if( nodeListPlacemarkItem.item(j).getNodeName().equals("Point")
                                || nodeListPlacemarkItem.item(j).getNodeName().equals("LineString")) {
//                                    Log.d("coordsList", nodeListPlacemarkItem.item(j).getTextContent().trim());
                                    String[] coords = nodeListPlacemarkItem.item(j).getTextContent().trim().split(" ");
//                                    Log.d("coords length", coords.length+"");
                                    for (int k=0; k<coords.length; k++) {
                                        cnt1++;
                                        curCoord = coords[k];
//                                        Log.d("blanks", ""+coords[k].equals(""));
//                                        Log.d("equals", curCoord+","+preCoord+" : "+(curCoord.equals(preCoord)));
                                        if (!(curCoord.equals("") || curCoord.equals(preCoord))) {
                                            cnt2++;
                                            String[] lonlat = curCoord.split(",");
//                                            Log.d("lonlat", Double.valueOf(lonlat[1])+","+Double.valueOf(lonlat[0]));
                                            coordsList.add(new TMapPoint(Double.valueOf(lonlat[1]), Double.valueOf(lonlat[0])));
                                        }
                                        preCoord = curCoord;
                                    }
                                }
                            }
                        }
                        Log.d("debug", "findPathAllData End");
//                        for (int t=0; t<coordsList.size(); t++) {
//                            Log.d("coordsList"+t, coordsList.get(t).getLatitude()+", "+coordsList.get(t).getLongitude());
//                        }


                        TMapPolyLine tMapPolyLine = new TMapPolyLine();

                        tMapPolyLine.setLineColor(Color.RED);
                        tMapPolyLine.setLineWidth(20);
                        tMapPolyLine.setOutLineAlpha(0);
                        for( int i=0; i<coordsList.size(); i++ ) {
                            tMapPolyLine.addLinePoint( coordsList.get(i) );
                        }
                        tMapView.addTMapPolyLine("Line", tMapPolyLine);
                        Log.d("polyLine", "draw polyLine");
                        Log.d("cnt1", String.valueOf(cnt1));
                        Log.d("cnt2", String.valueOf(cnt2));
                        Log.d("findPathAllData", ""+coordsList.size());
                        String tmp_data = coordsList.get(0).getLatitude()+","+coordsList.get(0).getLongitude();
                        byte[] bytes = tmp_data.getBytes();
                        Log.d("findPathAllData", tmp_data+" : "+tmp_data.length()+"");
                        Log.d("findPathAllData", "bytes : "+bytes+", "+bytes.length);
                        Log.d("findPathAllData", "bytes : "+tmp_data.getBytes()+", "+tmp_data.getBytes().length);
                        Log.d("findPathAllData", "string : "+new String(bytes)+", "+new String(bytes).length());
                    }

                });
            }
        }.start();




        /*
        // 클릭 이벤트 설정
        tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Toast.makeText(MainActivity.this, "onPress~!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onPressUpEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint, PointF pointF) {
                Toast.makeText(MainActivity.this, "onPressUp~!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 롱 클릭 이벤트 설정
        tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList arrayList, ArrayList arrayList1, TMapPoint tMapPoint) {
                Toast.makeText(MainActivity.this, "onLongPress~!", Toast.LENGTH_SHORT).show();
            }
        });

        // 지도 스크롤 종료
        tMapView.setOnDisableScrollWithZoomLevelListener(new TMapView.OnDisableScrollWithZoomLevelCallback() {
            @Override
            public void onDisableScrollWithZoomLevelEvent(float zoom, TMapPoint centerPoint) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "zoomLevel=" + zoom + "\nlon=" + centerPoint.getLongitude() + "\nlat=" + centerPoint.getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tMapView.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {
            @Override
            public void onEnableScrollWithZoomLevelEvent(float zoom, TMapPoint centerPoint) {
                Toast.makeText(MainActivity.this, "onEnable\n" + "zoomLevel=" + zoom + "\nlon=" + centerPoint.getLongitude() + "\nlat=" + centerPoint.getLatitude(), Toast.LENGTH_SHORT).show();
            }
        });

         */

        // MarkerItem2 풍선뷰 클릭 시 이벤트 발생
        tMapView.setOnMarkerClickEvent(new TMapView.OnCalloutMarker2ClickCallback() {
            @Override
            public void onCalloutMarker2ClickEvent(String id, TMapMarkerItem2 markerItem2) {
                String strMessage = "ClickEvent " + " id " + id + " " + markerItem2.latitude + " " + markerItem2.longitude;
                Toast.makeText(MainActivity.this, strMessage, Toast.LENGTH_SHORT).show();
                Log.d("setOnMarkerClickEvent", "onCalloutMarker2ClickEvent");
            }
        });
    }
}
