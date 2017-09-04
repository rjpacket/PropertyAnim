### 一、先看网易中奖奖池的效果
![wangyi.gif](http://upload-images.jianshu.io/upload_images/5994029-7cda47520a8caf9e.gif?imageMogr2/auto-orient/strip)

### 二、思路
明显的动画效果，而且是随着时间的推移，前面的数字暂停下来。数字的滚动用线程？！当然可以实现。但是前面的数字停下，这种效果不好处理。我开始想的是从最大的数字开始跑，一直减到0，在跑的过程中比对数字的位数，高位的就截取最终的结果，低位的显示当前的计数。同样的很麻烦，而且控制不了显示的位数，万一显示上亿，GG了。
换思路，我发现网易的动画里每个位置上的数字都是很随机的出现，不是规律的递减。那就获取最终结果的位数，然后线程里随机0到9：
```
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((int) Math.ceil(Math.random() * 9));
        }
        return sb.toString();
```
如果size是5，返回的一直是一个五位数，当很快速的刷新的时候，效果就很动画了。
上述代码实际测试可以实现。但是要想实现前面的数字暂停下来的效果，又没有了思路。

### 三、巧合
在搜索的时候，发现属性动画里有一个参数fraction，一直是从0到1（左右包含）的变化，感觉很有戏。因为这个暂停的数字看作进度，也是从\[0,1\]区间里变化。

#### 3.1 定义估值器
```
    public class NumberEvaluator implements TypeEvaluator<String> {
    
        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int size = endValue.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if(i * 1.0 / size > fraction) {
                    sb.append((int) Math.ceil(Math.random() * 9));
                }else{
                    sb.append(String.valueOf(endValue).charAt(i));
                }
            }
            return sb.toString();
        }
    }
```
这个类就是传入一个开始值和一个结束值，然后返回无数的中间值，这个fraction默认0到1变化（不知道可不可以改变）。我们遍历显示的位数，如果它与整体的比值大于fraction，说明进度还没有到，如果小于，说明已经过了这个进度，画个图示意一下：

![test.png](http://upload-images.jianshu.io/upload_images/5994029-8f598a46c167e6d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

就是随着时间的流逝，返回最终值得前几位和随机值的组合。

### 四、代码实现
```
        ValueAnimator numberAnim = ValueAnimator.ofObject(new NumberEvaluator(), "0", "676767676767");
        numberAnim.setDuration(3000);
        numberAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String number = (String) animation.getAnimatedValue();
                tvNumber.setText(number);
            }
        });
        numberAnim.start();
        numberAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvNumber.setText("676767676767");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
```
new一个定义的估值器扔进去，起始值不关心，传空，传一个最终的显示值，然后动画里不断的监听拿到中间值设置进去，动画结束，把最终的值设置上，结束。

### 五、最终效果

![number.gif](http://upload-images.jianshu.io/upload_images/5994029-566fb988aa6b2e86.gif?imageMogr2/auto-orient/strip)

附上[简书](http://www.jianshu.com/p/b5f76b572f81)地址，有兴趣可以肛一波。