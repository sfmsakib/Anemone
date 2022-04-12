package com.sprout.anemone
import android.app.Dialog
import android.content.Context
import kotlin.jvm.JvmOverloads
import android.widget.TextView
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.graphics.drawable.ColorDrawable
import androidx.annotation.StringRes
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.onoda.titas_gas_user.utils.awesome_dialog.AwesomeDialogCompat.DialogOnClickListenerAdapter
import com.sprout.aneone.R

abstract class AbsAnemoneDialog<Self: AbsAnemoneDialog<Self, T>, T: AbsAnemoneDialog<Self, T>> @JvmOverloads constructor(
    context: Context?,
    theme: Int = 0,
    layoutRes: Int = 0
) {
    private var dialog: Dialog? = null
    private var dialogView: View? = null
    private var iconView: ImageView? = null
    private var topTitleView: TextView? = null
    private var titleView: TextView? = null
    private var messageView: TextView? = null
    private fun init(dialogBuilder: AlertDialog.Builder, @LayoutRes res: Int) {
        dialogView = LayoutInflater.from(dialogBuilder.context).inflate(res, null)
        dialog = dialogBuilder.setView(dialogView).create()
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        iconView = findView<ImageView>(R.id.ld_icon)
        titleView = findView<TextView>(R.id.ld_title)
        messageView = findView<TextView>(R.id.ld_message)
        topTitleView = findView<TextView>(R.id.ld_top_title)
    }

    @get:LayoutRes
    protected abstract val layout: Int
    open fun configureView(viewViewConfigurator: ViewConfigurator<View?>): T {
        viewViewConfigurator.configureView(dialogView)
        return this as T
    }

    fun configureTitleView(viewConfigurator: ViewConfigurator<TextView?>): T {
        viewConfigurator.configureView(titleView)
        return this as T
    }

    fun configureMessageView(viewConfigurator: ViewConfigurator<TextView?>): T {
        viewConfigurator.configureView(messageView)
        return this as T
    }

    fun setMessage(@StringRes message: Int): T {
        return setMessage(string(message))
    }

    fun setMessage(message: CharSequence?): T {
        messageView!!.visibility = View.VISIBLE
        messageView!!.text = message
        return this as T
    }

    fun setTitle(@StringRes title: Int): T {
        return setTitle(string(title))
    }

    fun setTopTitle(@StringRes title: Int): T {
        return setTopTitle(string(title))
    }

    fun setTitle(title: CharSequence?): T {
        titleView!!.visibility = View.VISIBLE
        titleView!!.text = title
        return this as T
    }

    fun setTopTitle(title: CharSequence?): T {
        topTitleView!!.visibility = View.VISIBLE
        topTitleView!!.text = title
        return this as T
    }

    fun setTopTitleColor(color: Int): T {
        topTitleView!!.setTextColor(color)
        return this as T
    }

    fun setTitleColor(color: Int): T {
        titleView!!.setTextColor(color)
        return this as T
    }

    fun setIcon(bitmap: Bitmap?): T {
        iconView!!.visibility = View.VISIBLE
        iconView!!.setImageBitmap(bitmap)
        return this as T
    }

    fun setIcon(drawable: Drawable?): T {
        iconView!!.visibility = View.VISIBLE
        iconView!!.setImageDrawable(drawable)
        return this as T
    }

    fun setIcon(@DrawableRes iconRes: Int): T {
        iconView!!.visibility = View.VISIBLE
        iconView!!.setImageResource(iconRes)
        return this as T
    }

    fun setIconTintColor(iconTintColor: Int): T {
        iconView!!.setColorFilter(iconTintColor)
        return this as T
    }

    fun setTitleGravity(gravity: Int): T {
        titleView!!.gravity = gravity
        return this as T
    }

    fun setMessageGravity(gravity: Int): T {
        messageView!!.gravity = gravity
        return this as T
    }

    fun setTopColor(@ColorInt topColor: Int): T {
        findView<View>(R.id.ld_color_area).setBackgroundColor(topColor)
        return this as T
    }

    fun setTopColorRes(@ColorRes topColoRes: Int): T {
        return setTopColor(color(topColoRes))
    }

    /*
     * You should call method saveInstanceState on handler object and then use saved info to restore
     *  your dialog in onRestoreInstanceState. Static methods wasDialogOnScreen and getDialogId will
     * help you in this.
     */
    fun setInstanceStateHandler(id: Int, handler: AnemoneSaveStateHandler): T {
        handler.handleDialogStateSave(id, this)
        return this as T
    }

    fun setCancelable(cancelable: Boolean): T {
        dialog!!.setCancelable(cancelable)
        return this as T
    }

    fun setSavedInstanceState(savedInstanceState: Bundle?): T {
        if (savedInstanceState != null) {
            val hasSavedStateHere = savedInstanceState.keySet().contains(KEY_SAVED_STATE_TOKEN) &&
                    savedInstanceState.getSerializable(KEY_SAVED_STATE_TOKEN) === javaClass
            if (hasSavedStateHere) {
                restoreState(savedInstanceState)
            }
        }
        return this as T
    }

    fun show(): Dialog? {
        dialog!!.show()
        return dialog
    }

    fun create(): Dialog? {
        return dialog
    }

    fun dismiss() {
        dialog!!.dismiss()
    }

    open fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_SAVED_STATE_TOKEN, javaClass)
    }

    open fun restoreState(savedState: Bundle?) {}
    val isShowing: Boolean
        get() = dialog != null && dialog!!.isShowing

    protected fun string(@StringRes res: Int): String {
        return dialogView!!.context.getString(res)
    }

    protected fun color(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    protected val context: Context
        protected get() = dialogView!!.context

    protected fun <ViewClass : View?> findView(id: Int): ViewClass {
        return dialogView!!.findViewById<View>(id) as ViewClass
    }

    protected inner class ClickListenerDecorator(
        private val clickListener: View.OnClickListener?,
        private val closeOnClick: Boolean
    ) : View.OnClickListener {
        override fun onClick(v: View) {
            if (clickListener != null) {
                if (clickListener is DialogOnClickListenerAdapter) {
                    clickListener.onClick(dialog, v.id)
                } else {
                    clickListener.onClick(v)
                }
            }
            if (closeOnClick) {
                dismiss()
            }
        }
    }

    companion object {
        private const val KEY_SAVED_STATE_TOKEN = "key_saved_state_token"
    }

    init {
        var layoutRes = layoutRes
        if (layoutRes == 0) {
            layoutRes = layout
        }
        if (theme == 0) {
            init(AlertDialog.Builder(context!!), layoutRes)
        } else {
            init(AlertDialog.Builder(context!!, theme), layoutRes)
        }
    }
}