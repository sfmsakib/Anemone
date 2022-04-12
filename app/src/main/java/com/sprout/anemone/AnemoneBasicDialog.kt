package com.onoda.titas_gas_user.utils.awesome_dialog

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import com.onoda.titas_gas_user.R

class AnemoneBasicDialog : AbsAwesomeDialog<AnemoneBasicDialog, AnemoneBasicDialog> {
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null
    private var neutralButton: Button? = null
    private var yesNoButtonLayout: LinearLayout? = null
    private var neutralButtonLayout: LinearLayout? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, theme: Int) : super(context, theme) {}
    constructor(context: Context?, buttonLayout: ButtonLayout) : super(context,
        0,
        buttonLayout.layout) {
    }

    constructor(context: Context?, theme: Int, buttonLayout: ButtonLayout) : super(context,
        theme,
        buttonLayout.layout) {
    }

    fun setPositiveButton(@StringRes text: Int, listener: View.OnClickListener?): AnemoneBasicDialog {
        return setPositiveButton(string(text), listener)
    }

    fun setPositiveButton(text: String?, listener: View.OnClickListener?): AnemoneBasicDialog {
        yesNoButtonLayout!!.visibility = View.VISIBLE
        neutralButtonLayout!!.visibility = View.GONE
        positiveButton!!.visibility = View.VISIBLE
        positiveButton!!.text = text
        positiveButton!!.setOnClickListener(ClickListenerDecorator(listener, true))
        return this
    }

    fun setNegativeButtonText(@StringRes text: Int): AnemoneBasicDialog {
        return setNegativeButton(string(text), null)
    }

    fun setNegativeButtonText(text: String?): AnemoneBasicDialog {
        return setNegativeButton(text, null)
    }

    fun setNegativeButton(@StringRes text: Int, listener: View.OnClickListener?): AnemoneBasicDialog {
        return setNegativeButton(string(text), listener)
    }

    fun setNegativeButton(text: String?, listener: View.OnClickListener?): AnemoneBasicDialog {
        yesNoButtonLayout!!.visibility = View.VISIBLE
        neutralButtonLayout!!.visibility = View.GONE
        negativeButton!!.visibility = View.VISIBLE
        negativeButton!!.text = text
        negativeButton!!.setOnClickListener(ClickListenerDecorator(listener, true))
        return this
    }

    fun setNeutralButtonText(@StringRes text: Int): AnemoneBasicDialog {
        return setNeutralButton(string(text), null)
    }

    fun setNeutralButtonText(text: String?): AnemoneBasicDialog {
        return setNeutralButton(text, null)
    }

    fun setNeutralButton(@StringRes text: Int, listener: View.OnClickListener?): AnemoneBasicDialog {
        return setNeutralButton(string(text), listener)
    }

    fun setNeutralButton(text: String?, listener: View.OnClickListener?): AnemoneBasicDialog {
        yesNoButtonLayout!!.visibility = View.GONE
        neutralButtonLayout!!.visibility = View.VISIBLE
        neutralButton!!.visibility = View.VISIBLE
        neutralButton!!.text = text
        neutralButton!!.setOnClickListener(ClickListenerDecorator(listener, true))
        return this
    }

    fun setButtonsColor(@ColorInt color: Int): AnemoneBasicDialog {
        positiveButton!!.setTextColor(color)
        negativeButton!!.setTextColor(color)
        neutralButton!!.setTextColor(color)
        return this
    }

    fun setButtonsColorRes(@ColorRes colorRes: Int): AnemoneBasicDialog {
        return setButtonsColor(color(colorRes))
    }

    fun setOnButtonClickListener(listener: View.OnClickListener?): AnemoneBasicDialog {
        return setOnButtonClickListener(true, listener)
    }

    fun setOnButtonClickListener(
        closeOnClick: Boolean,
        listener: View.OnClickListener?
    ): AnemoneBasicDialog {
        val clickHandler: View.OnClickListener = ClickListenerDecorator(listener, closeOnClick)
        positiveButton!!.setOnClickListener(clickHandler)
        neutralButton!!.setOnClickListener(clickHandler)
        negativeButton!!.setOnClickListener(clickHandler)
        return this
    }

    fun setPositiveButtonText(@StringRes text: Int): AnemoneBasicDialog {
        return setPositiveButton(string(text), null)
    }

    fun setPositiveButtonText(text: String?): AnemoneBasicDialog {
        return setPositiveButton(text, null)
    }

    fun setPositiveButtonColor(@ColorInt color: Int): AnemoneBasicDialog {
        positiveButton!!.setTextColor(color)
        return this
    }

    fun setNegativeButtonColor(@ColorInt color: Int): AnemoneBasicDialog {
        negativeButton!!.setTextColor(color)
        return this
    }

    fun setNeutralButtonColor(@ColorInt color: Int): AnemoneBasicDialog {
        neutralButton!!.setBackgroundColor(color)
        return this
    }

    fun setNeutralButtonTextColor(@ColorInt color: Int): AnemoneBasicDialog {
        neutralButton!!.setTextColor(color)
        return this
    }

    fun setPositiveButtonColorRes(@ColorRes colorRes: Int): AnemoneBasicDialog {
        return setPositiveButtonColor(color(colorRes))
    }

    fun setNegativeButtonColorRes(@ColorRes colorRes: Int): AnemoneBasicDialog {
        return setNegativeButtonColor(color(colorRes))
    }

    fun setNeutralButtonColorRes(@ColorRes colorRes: Int): AnemoneBasicDialog {
        return setNeutralButtonColor(color(colorRes))
    }

    enum class ButtonLayout(@field:LayoutRes @param:LayoutRes val layout: Int) {
        HORIZONTAL(R.layout.dialog_alert_yes_no), VERTICAL(R.layout.dialog_alert_yes_no);
    }

    companion object {
        const val POSITIVE_BUTTON = R.id.ld_btn_yes
        const val NEGATIVE_BUTTON = R.id.ld_btn_no
        const val NEUTRAL_BUTTON = R.id.ld_btn_neutral
    }

    init {
        positiveButton = findView<Button>(R.id.ld_btn_yes)
        negativeButton = findView<Button>(R.id.ld_btn_no)
        neutralButton = findView<Button>(R.id.ld_btn_neutral)
        yesNoButtonLayout = findView<LinearLayout>(R.id.ld_ll_yes_no)
        neutralButtonLayout = findView<LinearLayout>(R.id.ld_ll_neutral)
    }

    override val layout: Int
        get() = R.layout.dialog_alert_yes_no
}