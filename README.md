# SliderView

A view that acts like a `UISlider` but has no visible interface. Pan to change its value.

###Properties: 

Just like `UISlider`: **value**, **maxValue**, **minValue**.
    
How far should user swipe before slider changes value: **resolution**.

Sensitivity to user's gestures: **sensitivity**.

You can set your own object as **indicator**, just conform to `SliderViewIndicator` protocol.

Or you can observe events using this delegate method: 

      func sliderView(_ sliderView: SliderView, valueChanged value: Float)



#SliderIndicator

A minimalistic view that can be set as **indicator* of SliderView.

###Properties:

Just like `SliderView`: **value**, **maxValue**, **minValue**.

Relative padding of the indicator: **relativePadding**

Defines the behaviour of the indicator: **anchor**. Can be `.left`, `.right` or `.center`.

Color of the indicator: **foregroundColour**.

