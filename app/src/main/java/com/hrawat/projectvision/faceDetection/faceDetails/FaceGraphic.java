/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hrawat.projectvision.faceDetection.faceDetails;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.hrawat.projectvision.ProjectVisionApplication;
import com.hrawat.projectvision.R;
import com.hrawat.projectvision.faceDetection.camera_util.GraphicOverlay;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {

    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;
    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;
    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;

    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);
        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];
        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);
        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);
        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    void setId(int id) {
        mFaceId = id;
    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
//        canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("happiness: " + String.format(Locale.ENGLISH, "%.2f", face.getIsSmilingProbability()),
//                x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format(Locale.ENGLISH, "%.2f", face.getIsRightEyeOpenProbability()),
//                x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format(Locale.ENGLISH, "%.2f", face.getIsLeftEyeOpenProbability()),
//                x - ID_X_OFFSET * 2, y - ID_Y_OFFSET * 2, mIdPaint);
        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        ////////////////////////////////////
        int mright = Math.round(right);
        int mbottom = Math.round(bottom);
//        drawFaceAnnotations(canvas);
        canvas.drawRect(left, top, right, bottom, mBoxPaint);
//        double scale = Math.min(canvas.getWidth(), canvas.getHeight());
//        int left_cx = 0;
//        int left_cy = 0;
//        int right_cx = 0;
//        int right_cy = 0;
//        for (Landmark landmark : mFace.getLandmarks()) {
//            if (landmark.getType() == Landmark.LEFT_EYE) {
//                left_cx = (int) (landmark.getPosition().x * scale);
//                left_cy = (int) (landmark.getPosition().y * scale);
//            } else if (landmark.getType() == Landmark.RIGHT_EYE) {
//                right_cx = (int) (landmark.getPosition().x * scale);
//                right_cy = (int) (landmark.getPosition().y * scale);
//            }
//        }
        Drawable d = ProjectVisionApplication.context().getResources().getDrawable(R.drawable.ic_sunglasses_clear);
//        d.setBounds(new Rect(Math.round(x-mright/2), Math.round(y-mbottom/2),mright-100,mbottom/3));
        d.setBounds(new Rect(Math.round(x - 200), Math.round(y - 120), Math.round(x + 220), Math.round(y + 120)));
//            d.setBounds(new Rect(left_cx - 50, left_cy - 50, Math.round(right - 50), Math.round(bottom - 50)));
        d.draw(canvas);
    }

    private void drawFaceAnnotations(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        double scale = Math.min(canvas.getWidth(), canvas.getHeight());
        for (Landmark landmark : mFace.getLandmarks()) {
            int cx = (int) (landmark.getPosition().x * scale);
            int cy = (int) (landmark.getPosition().y * scale);
            canvas.drawCircle(cx, cy, 10, paint);
        }
    }
}
