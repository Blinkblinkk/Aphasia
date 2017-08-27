package com.example.musicmanpop.aphasiatalkhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF000000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap cvBitmap;
    private float brushSize, lastBrushSize, posTxtX,posTxtY;
    private boolean erase;
    private boolean text;
    //private String letters;
    private Context context;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        setupDrawing();
    }

    private void setupDrawing(){
        // brushSize = getResources().getInteger(R.integer.medium_size);
        brushSize = 12;
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        text = false;
        erase = false;
        //letters = "";
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);
        cvBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(cvBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(cvBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
        //if(text) canvas.drawText(s, posTxtX, posTxtY, drawPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!text)
                    drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!text) drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                if(text){
				/*((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
				letters = "";*/
                    final EditText ed = new EditText(context);
                    ed.requestFocus();
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(240, 240);

                    RelativeLayout r = new RelativeLayout(context);
                    r.setBackgroundColor(Color.TRANSPARENT);
                    r.setGravity(Gravity.CENTER);
                    r.addView(ed);
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Typing...")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    setText(ed.getText().toString());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    setTextMode(false);
                                }
                            })
                            .setView(r)
                            .create();
                    alertDialog.show();
                    posTxtX = touchX;
                    posTxtY = touchY;
                }
                else drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setColor(paintColor);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }

    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void setEraseMode(boolean isErase){
        erase=isErase;
        if(erase){
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else drawPaint.setXfermode(null);
    }

    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setTextMode(boolean isTyping){
		/*if(text&&!isTyping){//check the softKeyboard is shown first, and then disable it
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(this.getWindowToken(), 0);
		}*/
        text = isTyping;
        if(text){
            drawPaint.setTextSize(40);
            drawPaint.setStrokeWidth(3);
            //this.setFocusable(true);
        }
    }

    public void setText(String message){
        //letters += letter;
        drawCanvas.drawText(message, posTxtX, posTxtY, drawPaint);
    }
}

