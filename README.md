# SliderView

A view that acts like a `UISlider` but has no visible interface. Pan in any direction to change its value.

#### Properties: 

Just like `UISlider`: **value**, **maxValue**, **minValue**.
    
How far should user swipe before slider changes value: **resolution**.

Sensitivity to user's gestures: **sensitivity**.

You can set your own object as **indicator**, just conform to `SliderViewIndicator` protocol.

Or you can observe events using this delegate method: 

      func sliderView(_ sliderView: SliderView, valueChanged value: Float)



# SliderViewHorizontalIndicator

A minimalistic view that can be set as **indicator** of SliderView.

#### Properties:

Just like `SliderView`: **value**, **maxValue**, **minValue**.

Relative padding of the indicator: **relativePadding**

Defines the behaviour of the indicator: **anchor**. Can be `.left`, `.right` or `.center`.

Colour of the indicator: **foregroundColour**.


<img src="https://github.com/ysoftware/SliderView/blob/master/1.png" alt="alt text" width="400">
<img src="https://github.com/ysoftware/SliderView/blob/master/2.png" alt="alt text" width="400">
<img src="https://github.com/ysoftware/SliderView/blob/master/3.png" alt="alt text" width="400">
<img src="https://github.com/ysoftware/SliderView/blob/master/4.png" alt="alt text" width="400">
