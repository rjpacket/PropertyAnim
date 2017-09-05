### 一、先看掌阅动画效果
![掌阅动画.gif](http://upload-images.jianshu.io/upload_images/5994029-239783fcfe5f9c6d.gif?imageMogr2/auto-orient/strip)

### 二、分析
从动画上看，可以拆解分为三部分进行：
1. 书壳的翻转动画；
2. 书扉页的放大动画；
3. 书壳的放大动画。（这个动画和扉页是同步进行的，但是仔细看，可以看到，书壳的高度变化速率和扉页是一样的，但是宽度的速率要低于扉页）

### 三、代码实现
我们选择用属性动画实现，那么首先要确定哪些属性是变化的。首先扉页放大的过程中，宽高是变化的，随着宽高变化，位置也需要调整，至少需要记录left和top。那就是至少需要四个值。但是宽可以通过
> 宽度 = right - left
> 高度 = bottom - top

索性我们建立一个对象来存储每一本书的（left, top, right, bottom）。
```
public class BookValue {
    private int left;
    private int top;
    private int right;
    private int bottom;
}
```
这样我们问题就变成了，如果知道起始和终点的BookValue，那么就可以计算出中间如何一个位置的BookValue。有没有很熟悉？没错就是属性动画的自定义估值器。
```
public class BookEvaluator implements TypeEvaluator<BookValue> {
    @Override
    public BookValue evaluate(float fraction, BookValue startValue, BookValue endValue) {
        int startLeft = startValue.getLeft();
        int startTop = startValue.getTop();
        int startRight = startValue.getRight();
        int startBottom = startValue.getBottom();

        int endLeft = endValue.getLeft();
        int endTop = endValue.getTop();
        int endRight = endValue.getRight();
        int endBottom = endValue.getBottom();

        BookValue bookValue = new BookValue();
        bookValue.setLeft((int) (startLeft - fraction * (startLeft - endLeft)));
        bookValue.setTop((int) (startTop - fraction * (startTop - endTop)));
        bookValue.setRight((int) (startRight - fraction * (startRight - endRight)));
        bookValue.setBottom((int) (startBottom - fraction * (startBottom - endBottom)));

        return bookValue;
    }
}
```
新建一个估值器，传进去开始和结束的BookValue，计算返回中间的BookValue。计算不说了，那么怎么确定开始和结束位置呢，结束位置很好确定，就是整个屏幕。（实际操作中发现需要减去状态栏的高度，如果追求精度的话需要考虑，我们这里没有考虑）
那么起始位置怎么确定呢？我们点击的时候是可以拿到当前这个View的，这个View的属性里面就带了需要的位置信息。
```
convertView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ViewGroup window = (ViewGroup) getWindow().getDecorView();
        BookValue startValue = new BookValue(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        BookValue endValue = new BookValue(window.getLeft(), window.getTop(), window.getRight(), window.getBottom());

        BookView bookView = new BookView(mContext);
        window.addView(bookView);
        bookView.startAnim(startValue, endValue);
    }
});            
```
获取屏幕的DecorView，可以说这个view的位置就是结束位置。onClick里面的view就是点击的开始位置。点击的view里面有两个子View，一个是书壳一个是书扉页，我们可以把两个view组合成一个自定义的ViewGroup：
```
public class BookView extends FrameLayout{

    private TextView tvBookName;
    private TextView tvBookAuthor;

    public BookView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BookView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BookView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_book_view, this);
        setBackgroundColor(Color.TRANSPARENT);
        tvBookName = (TextView) findViewById(R.id.tv_book_name);
        tvBookAuthor = (TextView) findViewById(R.id.tv_book_author);
    }
}
```
BookView继承自Framelayout，再看它的布局：
```
<?xml version="1.0" encoding="utf-8"?>
<merge xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent">

    <TextView
        android:id="@+id/tv_book_author"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="作者：\n小\n黑"
        android:gravity="center"
        android:background="#6d4c7c"
        android:textColor="#fff"
        />

    <TextView
        android:id="@+id/tv_book_name"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="android\n入\n门\n到\n放\n弃"
        android:gravity="center"
        android:background="#4d3b3b"
        android:textColor="#fff"
        />
</merge>
```
只有两个TextView，因为BookView是Framelayout，所以书壳在扉页的下面。细心的人可以看到BookView我们设置背景透明的，方便动画实现，省去了还要计算外层布局的位置，直接让它透明的充满全屏。
我们来看BookView里面的实现：
```
    /**
     * 开启动画
     */
    public void startAnim(BookValue startValue, BookValue endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BookEvaluator(), startValue, endValue);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);

        tvBookName.setPivotX(0);
        tvBookName.setPivotY(500);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvBookName, "rotationY", 0, -180);
        objectAnimator.setDuration(1000);
        objectAnimator.setStartDelay(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
    }
```
暴露一个开启动画的方法，动画上面知道肯定是两个（实际上是三个，这里简化了，所以效果也有点粗糙）：
> ValueAnimator valueAnimator = ValueAnimator.ofObject(new BookEvaluator(), startValue, endValue);

新建一个估值器，直接扔进属性动画里，这里不需要指定动画的作用对象，因为它实际就是开启了一个计算，不断的回调计算结果。那么回调去哪了呢？后面再说。再看第二个动画：
```
  tvBookName.setPivotX(0);
  tvBookName.setPivotY(500);
  ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvBookName, "rotationY", 0, -180);
  objectAnimator.setDuration(1000);
  objectAnimator.setStartDelay(300);
```
这个动画是作用于书壳上的，首先要设定锚点，不然它会默认中间翻转。属性动画设置rotationY属性变化，谁的属性？第一个参数tvBookName。怎么变化？从0到-180，也就是逆时针。
锚点的PivotX等于0很好理解，就是绕y轴翻转，但是PivotY怎么理解呢？我也不清楚，但是设置了一个很大的值，它和屏幕会有一个夹角，很接近掌阅的效果。
还有一个延迟300ms，书壳是先随着扉页一起放大，再翻转，效果上就很真实了。

上面有说计算结果回调去哪了，有重写的方法回调：
```
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        BookValue midBookValue = (BookValue) animation.getAnimatedValue();

        tvBookName.setX(midBookValue.getLeft());
        tvBookName.setY(midBookValue.getTop());
        ViewGroup.LayoutParams layoutParams = tvBookName.getLayoutParams();
        layoutParams.width = midBookValue.getRight() - midBookValue.getLeft();
        layoutParams.height = midBookValue.getBottom() - midBookValue.getTop();
        tvBookName.setLayoutParams(layoutParams);

        tvBookAuthor.setX(midBookValue.getLeft());
        tvBookAuthor.setY(midBookValue.getTop());
        ViewGroup.LayoutParams layoutParams1 = tvBookAuthor.getLayoutParams();
        layoutParams1.width = midBookValue.getRight() - midBookValue.getLeft();
        layoutParams1.height = midBookValue.getBottom() - midBookValue.getTop();
        tvBookAuthor.setLayoutParams(layoutParams1);
    }
```
首先获取midBookValue，这个值是不断的返回的，所以下面的方法要不断的修改作用对象的属性，就形成了动画的视觉效果。那么我们需要修改哪些属性呢？
首先修改X与Y，这个书壳就会产生偏移（这里不能去设置left和top，会有坑，一直显示在屏幕的原点位置。至于原因，还没发现）。
然后修改书壳的宽高，是通过获取LayoutParams修改width和height实现的。
书扉页也需要同样的操作。
以上就是全部的思路。
### 四、最终效果
![电子书动画.gif](http://upload-images.jianshu.io/upload_images/5994029-b0dd7dee9c3f9a26.gif?imageMogr2/auto-orient/strip)

这样看起来有点粗糙，是因为书壳和扉页宽度的变化是一致的，我们可以将书壳的宽度做一下限制，或者将他们的动画分开来写。

附上[简书](http://www.jianshu.com/p/f104a287ebfa)地址，有兴趣可以肛一波。