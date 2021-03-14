package com.example.tmaptest;

import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.view.WindowManager;

public class MarkerOverlay extends TMapMarkerItem2 {

    private DisplayMetrics dm = null;

    private Context 	mContext = null;
    private BalloonOverlayView balloonView = null;
    private int mAnimationCount = 0;
    private TMapView mMapView;
    private int balloonX, balloonY;
    private Canvas cvs;
    private boolean showCallout;
    private int marginX, marginY;

    @Override
    public Bitmap getIcon() {
        return super.getIcon();
    }

    @Override
    public void setIcon(Bitmap bitmap) {
        super.setIcon(bitmap);
    }

    @Override
    public void setTMapPoint(TMapPoint point) {
        super.setTMapPoint(point);
    }

    @Override
    public TMapPoint getTMapPoint() {
        return super.getTMapPoint();
    }

    @Override
    public void setPosition(float dx, float dy) {
        super.setPosition(dx, dy);
    }

    /**
     * 풍선뷰 영역을 설정한다.
     */
    @Override
    public void setCalloutRect(Rect rect) {
        super.setCalloutRect(rect);
    }

    public MarkerOverlay(Context context, String labelName, String id) {
        this.mContext = context;

        dm = new DisplayMetrics();
        WindowManager wmgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wmgr.getDefaultDisplay().getMetrics(dm);

        balloonView = new BalloonOverlayView(mContext, labelName, id);

        balloonView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        balloonView.layout(0, 0, balloonView.getMeasuredWidth(), balloonView.getMeasuredHeight());
    }

    public MarkerOverlay(Context context, String contents1, String contents2, String contents3) {
        this.mContext = context;

        dm = new DisplayMetrics();
        WindowManager wmgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wmgr.getDefaultDisplay().getMetrics(dm);

        balloonView = new BalloonOverlayView(mContext, contents1, contents2, contents3);

        balloonView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        balloonView.layout(0, 0, balloonView.getMeasuredWidth(), balloonView.getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas, TMapView mapView, boolean showCallout) {

        mMapView = mapView;
        this.showCallout = showCallout;

        int x = mapView.getRotatedMapXForPoint(getTMapPoint().getLatitude(), getTMapPoint().getLongitude());
        int y = mapView.getRotatedMapYForPoint(getTMapPoint().getLatitude(), getTMapPoint().getLongitude());

        balloonX = x;
        balloonY = y;

        cvs = canvas;

        canvas.save();
        canvas.rotate(-mapView.getRotate(), mapView.getCenterPointX(), mapView.getCenterPointY());

        float xPos = getPositionX();
        float yPos = getPositionY();

        int nPos_x, nPos_y;

        int nMarkerIconWidth = 0;
        int nMarkerIconHeight = 0;
        marginX = 0;
        marginY = 0;

        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pin_r_m_p);
        setIcon(icon);

        nMarkerIconWidth = getIcon().getWidth();
        nMarkerIconHeight = getIcon().getHeight();

        nPos_x = (int) (xPos * nMarkerIconWidth);
        nPos_y = (int) (yPos * nMarkerIconHeight);

        if(nPos_x == 0) {
            marginX = nMarkerIconWidth / 2;
        } else {
            marginX = nPos_x;
        }

        if(nPos_y == 0) {
            marginY = nMarkerIconHeight / 2;
        } else {
            marginY = nPos_y;
        }

        canvas.translate(balloonX - marginX, balloonY - marginY);
        canvas.drawBitmap(getIcon(), 0, 0, null);
        canvas.restore();

        if (showCallout) {
            canvas.save();
            canvas.rotate(-mapView.getRotate(), mapView.getCenterPointX(), mapView.getCenterPointY());

            balloonView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int nTempX =  x - balloonView.getMeasuredWidth() / 2;
            int nTempY =  y - marginY - balloonView.getMeasuredHeight();

            canvas.translate(nTempX, nTempY);
            balloonView.draw(canvas);

            Rect rect = new Rect();

            // 풍선뷰 영역 설정
            rect.left = nTempX;
            rect.top = nTempY;
            rect.right = rect.left + balloonView.getMeasuredWidth();
            rect.bottom = rect.top + balloonView.getMeasuredHeight();

            setCalloutRect(rect);
            canvas.restore();
        }
    }

    public void drawBalloonView(Canvas canvas, TMapView mapView, int x, int y) {
//        Log.d("MarkerOverlay", "drawBalloonView called");

        //풍선뷰 그리기
        canvas.save();
        canvas.rotate(-mapView.getRotate(), mapView.getCenterPointX(), mapView.getCenterPointY());

        balloonView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        int nTempX =  balloonX - balloonView.getMeasuredWidth()/2;
        int nTempY =  balloonY - (getIcon().getHeight()+balloonView.getMeasuredHeight());

        canvas.translate(nTempX, nTempY);
        balloonView.draw(canvas);
        canvas.restore();

    }

    // MarkerItem2 아이콘 클릭 시 이벤트 발생
    public boolean onSingleTapUp(PointF point, TMapView mapView) {
        mapView.showCallOutViewWithMarkerItemID(getID());
        drawBalloonView(cvs, mMapView, balloonX, balloonY);
        return false;
    }

    Handler mHandler = null;

    @Override
    public void startAnimation() {
        super.startAnimation();

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (getAnimationIcons().size() > 0) {
                    if(mAnimationCount >= getAnimationIcons().size())
                        mAnimationCount = 0;

                    setIcon(getAnimationIcons().get(mAnimationCount));
                    mMapView.postInvalidate();
                    mAnimationCount++;
                    mHandler.postDelayed(this, getAniDuration());
                }
            }
        };

        mHandler = new Handler();
        mHandler.post(mRunnable);
    }
}
