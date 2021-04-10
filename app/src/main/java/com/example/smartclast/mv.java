package com.example.smartclast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class mv extends View {
    Paint paint;
    List<Float> wdlist = new ArrayList<>();
    List<Float> gzlist = new ArrayList<>();
    List<Integer> numlist = new ArrayList<>();
    int num = 400;
    int num2 = 0;

    public mv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);

        float startX = 40;
        float startY = 40;
        float stopX = getWidth() - 40;
        float stopY = getHeight() - 40;
        float spaceX = (stopX-startX)/6;
        float spaceY = (stopY-startY)/5;

        canvas.drawLine(0,0,getWidth(),0,paint);
        canvas.drawLine(0,0,0,getHeight(),paint);
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);
        canvas.drawLine(getWidth(),0,getWidth(),getHeight(),paint);

        if(num2!=0){
            numlist.add(num2);
            if(numlist.size()>4) numlist.remove(0);
        }
        num2++;
        for(int i=0;i<5;i++){
            canvas.drawText((num/4*i)+"",0,stopY-spaceY*i,paint);
            canvas.drawLine(startX,stopY-i*spaceY,stopX,stopY-i*spaceY,paint);

        }

        for(int i=0;i<numlist.size();i++) canvas.drawText(numlist.get(i)+"",startX+spaceX*(i+1),stopY+15,paint);

        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        canvas.drawLine(startX+1*spaceX,stopY+25,startX+1*spaceX+10,stopY+25,paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(startX+2*spaceX,stopY+25,startX+2*spaceX+10,stopY+25,paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("湿度",startX+2*spaceX+10,stopY+30,paint);
        canvas.drawText("温度",startX+1*spaceX+10,stopY+30,paint);

        paint.setStrokeWidth(15 );
        for(int i=0;i<gzlist.size();i++){
            float h1=stopY-wdlist.get(i)*spaceY/num;
            float h2=stopY-gzlist.get(i)*spaceY/num;
            paint.setColor(Color.RED);
            canvas.drawLine(startX+spaceX*(i+1)-5,stopY,startX+spaceX*(i+1)-5,h1,paint);
            paint.setColor(Color.BLUE);
            canvas.drawLine(startX+spaceX*(i+1)+15,stopY,startX+spaceX*(i+1)+15,h2,paint);
        }
    }

    public void send(List<Float> list1, List<Float> list2){

        wdlist.clear();
        gzlist.clear();
        float max=0;
        for(int i=0;i<list1.size();i++){
            wdlist.add(list1.get(i));
            gzlist.add(list2.get(i));
            if(list1.get(i)>max) max=list1.get(i);
            if(list2.get(i)>max) max=list2.get(i);
        }
        if(max>100){
            num=1000;
        }else{
            num=400;
        }

        postInvalidate();
    }
}

