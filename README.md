# PathMeasureSamples

## 效果图
![效果图1](https://raw.githubusercontent.com/houtrry/PathMeasureSamples/master/img/gif1.gif)
![效果图2](https://raw.githubusercontent.com/houtrry/PathMeasureSamples/master/img/gif3.gif)
![效果图3](https://raw.githubusercontent.com/houtrry/PathMeasureSamples/master/img/gif2.gif)

## 实现
### 波浪线（正弦曲线）
 使用二次贝塞尔曲线绘制。主要是Path#quadTo和Path#rQuadTo.
### PathMeasure 相关
1. 构造方法 public PathMeasure() 和 public PathMeasure(Path path, boolean forceClosed)。带参的构造方法中，path是要测量的路径，该path不一定是封闭的，如果不是闭合的path，那么forceClosed的作用就体现出来了，forceClosed为true的时候，如果path没有闭合，那么，测量的时候就会连接path的起始点结束点，构成一个闭合的曲线后再进行测量，forceClosed为false时，不会连接起始点结束点，直接测量path。
2. PathMeasure#setPath(Path path, boolean forceClosed)： 设置或者更新path。如果PathMeasure使用的是无参的构造方法，则需要使用这个方法来关联path。注意： 如果path内容有变化，则需要重新调用这个方法来更新path。
3. PathMeasure#getLength： 获取当前path的长度。这个长度受到forceClosed的影响。
4. PathMeasure#getPosTan(float distance, float pos[], float tan[])： 获取path上指定长度的地方在View坐标系中的位置，以及在该位置上的斜率（tan值）。比如第一个效果图中计算小船的位置，以及该位置上小船的倾斜角度，可以使用该方法。参数pos 和 tan可以传null。
5. PathMeasure#getMatrix(float distance, Matrix matrix, int flags)：可以参考上面的PathMeasure#getPosTan方法，只不过这次将获取到的数据放在了参数matrix中。flags可传的值有POSITION_MATRIX_FLAG 和 TANGENT_MATRIX_FLAG，分别表示获取位置和斜率，如果两个都需要获取，可以传POSITION_MATRIX_FLAG|TANGENT_MATRIX_FLAG。该方法可以用来实现效果图1中小船移动的效果。
6. PathMeasure#getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)： 截取从path中startD位置开始到stopD位置结束的path。startD在path中的起始位置，stopD在path中的结束位置，dst用来存放截取到的path，startWithMoveTo表示是否以path的起点位置为dst的起点位置（startWithMoveTo为true的时候，把path的起点位置作为dst的起点位置，为false的时候，dst的起点位置是默认的(0,0)点）。该方法可以用来实现效果图2中的效果。
7. PathMeasure#nextContour： 这个主要是针对一个path中包含多个分段的path的情况，比如说第三个效果图中的文字，通过Paint.getTextPath方法获取到的文字的path包含多个小的不连接的path，这种情况下，我们刚开始PathMeasure#getLength获取到的只是这众多小path中的第一段path的长度，如果我们希望从当前小path切换到下一个小的path，就使用PathMeasure#nextContour，如果后面还有小段path，则返回true并切换到下一个，如果后面没有了就返回false。该方法用来实现效果图3中文字的效果。



## 补充效果
![效果图](https://raw.githubusercontent.com/houtrry/PathMeasureSamples/master/img/gif4.gif)
### 实现方法
这个实现跟PathMeasure就没关系了。这需要用到另外一个类android.graphics.Region。  
Region可以用来获取Path和既定Region相交部分的矩形区域。
具体实现可以参见代码。




