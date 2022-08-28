package com.mouth.pad.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.mouth.pad.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;

public class GlideLoadUtils {

    /***
     *  加载圆角图片
     *  解决imageView在布局文件设置android:scaleType="centerCrop"属性后，圆角无效的问题
     * @param placeHolderID 占位图
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadRoundCornerImg(ImageView target, String url, int placeHolderID, int round) {

        try {
            RequestOptions options = new RequestOptions()
                    .transform(new CenterCrop())
                    .placeholder(placeHolderID);
            GlideApp.with(target.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .into(new BitmapImageViewTarget(target) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                                    .create(target.getContext().getResources(), resource);
                            roundedBitmapDrawable.setCornerRadius(round);
                            target.setImageDrawable(roundedBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载方角图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadImgNo(Context context, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions();
            GlideApp.with(context)
                    .load(url)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载方角图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadOverrideImg(Context context, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//关键代码，加载原始大小
                    .format(DecodeFormat.PREFER_RGB_565)
                    .transform(new CenterCrop());
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载方角图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadImg(Context context, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new CenterCrop());
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImg(Context context, ImageView target, Drawable drawable) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new CenterCrop());
            GlideApp.with(context)
                    .load(drawable)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadGifImg(Context context, ImageView target, int drawable) {
        try {
            RequestOptions options = new RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            GlideApp.with(context)
                     .asGif()
                    .apply(options)
                    .load(drawable)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleRoundImg(Context context, ImageView target, String url, int round) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new MultiTransformation(new CenterCrop(),
                            new RoundedCorners(round)));
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleRoundImg(Context context, ImageView target, String url, int round,int placeHolderID) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new MultiTransformation(new CenterCrop(),
                            new RoundedCorners(round)))
                    .placeholder(placeHolderID);
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleRoundImg(Context context, ImageView target, File url, int round) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new MultiTransformation(new CenterCrop(),
                            new RoundedCorners(round)));
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadRoundImg(Context context, ImageView target, String url, int round,int placeHolderID) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new RoundedCorners(round))
                    .placeholder(placeHolderID);
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param placeHolderID 占位图
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleImg(Context context, int placeHolderID, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new CenterCrop())
                    .placeholder(placeHolderID);
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .apply(RequestOptions.circleCropTransform())
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleImg(Context context, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .transform(new CenterCrop());
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .apply(RequestOptions.circleCropTransform())
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 圆形图片外边带一个圈, 经常用于加载头像
     */
    public static void loadCircleImgBorder(Context context, int placeHolderID, ImageView target, String url) {
        try {
            RequestOptions options = new RequestOptions().transform(new CenterCrop())
                    .transform(new GlideCircleTransformWithBorder(context, 2,
                            context.getResources().getColor(R.color.color_white)))
                    .placeholder(placeHolderID);
            GlideApp.with(context)
                    .load(url)
                    .apply(options)
                    .into(target);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角bitmap
     *
     * @param context
     * @param placeHolderID
     * @param target
     * @param bitmap
     */
    public static void loadRoundImgWithBitmap(final Context context, int placeHolderID, final ImageView target, Bitmap bitmap) {
        try {
            RequestOptions options = RequestOptions.circleCropTransform().placeholder(placeHolderID);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            GlideApp.with(context)
                    .load(bytes)
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class GlideCircleTransformWithBorder extends BitmapTransformation {
        private Paint mBorderPaint;
        private float mBorderWidth;

        public GlideCircleTransformWithBorder(Context context) {
        }

        public GlideCircleTransformWithBorder(Context context, int borderWidth, int borderColor) {
            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }


        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            if (mBorderPaint != null) {
                float borderRadius = r - mBorderWidth / 2;
                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
            }
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

    }


}