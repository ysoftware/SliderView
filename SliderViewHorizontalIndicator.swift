//
//  SliderIndicator.swift
//  https://github.com/ysoftware
//
//  Copyright © 2017 Yaroslav Erohin. All rights reserved.
//

import UIKit

@IBDesignable
class SliderViewHorizontalIndicator: UIView, SliderViewIndicator {

    // MARK: - Properties

    /** Lowest value of the indicator. Cannot be higher than `maxValue`. Default value is 0.0. */
    @IBInspectable var minValue:Float = 0.0 {
        didSet {
            if minValue >= maxValue { minValue = maxValue - 0.1 }
            reloadViews()
        }
    }

    /** Highest value of the indicator. Cannot be lower than `minValue`. Default value is 1.0. */
    @IBInspectable var maxValue:Float = 1.0 {
        didSet {
            if minValue >= maxValue { maxValue = minValue + 0.1 }
            reloadViews()
        }
    }

    /** Current value of the indicator. Default value is `0.5`. */
    @IBInspectable var value:Float = 0.5 {
        didSet {
            if value > maxValue { value = maxValue }
            else if value < minValue { value = minValue }
            reloadViews()
        }
    }

    /** Relative padding of the indicator. From `0.0` to `1.0`. Default value is `0`. */
    @IBInspectable var relativePadding:Float = 0 {
        didSet {
            if relativePadding >= 1 { relativePadding = 0.99 }
            else if relativePadding < 0 { relativePadding = 0 }
            reloadViews()
        }
    }

    /** Colour of the indicator. Default value is `black`.

     To set background colour, use property of `UIView` called `backgroundColor`. */
    @IBInspectable var foregroundColour: UIColor = .white {
        didSet {
            foregroundView.backgroundColor = foregroundColour
        }
    }

    /** Defines the behaviour of the indicator. Default value is `center`. */
    var anchor:Anchor = .center { didSet { reloadViews() }}

    /** Behaviour of the SliderIndicator. */
    enum Anchor {
        /** Goes from left side all the way to the right. */
        case left
        /** Goes from right side all the way to the left. */
        case right
        /** Middle point is at the center, `minValue` on the left, `maxValue` is on the right side.  */
        case center
    }

    /** Middle point between `minValue` and `maxValue`. */
    var middleValue:Float {
        return (minValue + maxValue) / 2
    }

    // MARK: - Private

    private let foregroundView = UIView()

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(foregroundView)
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        addSubview(foregroundView)
    }

    override func layoutSubviews() {
        super.layoutSubviews()

        reloadViews()
    }

    private func reloadViews() {
        UIView.animate(withDuration: 0.1,
                       delay: 0,
                       usingSpringWithDamping: 0.8,
                       initialSpringVelocity: 0,
                       options: .curveEaseOut,
                       animations: {
                        self.foregroundView.backgroundColor = self.foregroundColour

                        let dif = self.maxValue - self.minValue
                        let width = Float(self.frame.size.width)

                        switch self.anchor {
                        case .center:
                            let isPositive = self.middleValue - self.value < 0
                            let relativeValue = self.middleValue - self.value
                            let center = width / 2
                            let indicatorWidth = center - (center * self.relativePadding)
                            let position = abs(indicatorWidth / dif * 2 * relativeValue)

                            self.foregroundView.frame = CGRect(
                                x: CGFloat(isPositive ? center : center - position),
                                y: 0,
                                width: CGFloat(position),
                                height: self.frame.size.height)
                        case .left, .right:
                            let indicatorWidth = width - (width * self.relativePadding)
                            let position = indicatorWidth * (self.value - self.minValue)
                                / (self.maxValue - self.minValue)

                            self.foregroundView.frame = CGRect(
                                x: self.anchor == .left ? 0 : CGFloat(width - position),
                                y: 0,
                                width: CGFloat(position),
                                height: self.frame.size.height)
                        }
        }, completion: nil)
    }
    
}
