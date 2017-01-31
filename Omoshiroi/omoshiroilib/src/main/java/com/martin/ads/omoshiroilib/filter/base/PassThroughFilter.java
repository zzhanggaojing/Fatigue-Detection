package com.martin.ads.omoshiroilib.filter.base;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.martin.ads.omoshiroilib.R;
import com.martin.ads.omoshiroilib.glessential.object.Plain;
import com.martin.ads.omoshiroilib.glessential.program.GLPassThroughProgram;
import com.martin.ads.omoshiroilib.util.MatrixUtils;
import com.martin.ads.omoshiroilib.util.TextureUtils;

/**
 * Created by Ads on 2017/1/27.
 */

public class PassThroughFilter extends AbsFilter {

    protected GLPassThroughProgram glPassThroughProgram;
    private Plain plain;

    protected Context context;
    protected float[] projectionMatrix = new float[16];

    public PassThroughFilter(Context context) {
        this.context=context;
        glPassThroughProgram=new GLPassThroughProgram(context);
        plain=new Plain(true);

    }

    @Override
    public void init() {
        glPassThroughProgram.create();
    }

    @Override
    public void onPreDrawElements() {
        super.onPreDrawElements();
    }

    @Override
    public void destroy() {
        glPassThroughProgram.onDestroy();
    }

    @Override
    public void onDrawFrame(int textureId) {
        onPreDrawElements();
        glPassThroughProgram.use();
        Matrix.setIdentityM(projectionMatrix,0);
        plain.uploadTexCoordinateBuffer(glPassThroughProgram.getMaTextureHandle());
        plain.uploadVerticesBuffer(glPassThroughProgram.getMaPositionHandle());
        GLES20.glUniformMatrix4fv(glPassThroughProgram.getMVPMatrixHandle(), 1, false, projectionMatrix, 0);
        TextureUtils.bindTexture2D(textureId, GLES20.GL_TEXTURE0,glPassThroughProgram.getTextureSamplerHandle(),0);
        GLES20.glViewport(0,0,surfaceWidth,surfaceHeight);
        plain.draw();
    }

    @Override
    public void onFilterChanged(int surfaceWidth, int surfaceHeight) {
        super.onFilterChanged(surfaceWidth, surfaceHeight);
    }
}
