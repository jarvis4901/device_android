package com.bepi.ugandroid.ui.common

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.bepi.ugandroid.R
import java.lang.ref.WeakReference

/**
 * 自定义键盘fragment
 */
class CustomKeyboardDialogFragment : DialogFragment(), CustomEditText.OnTextInputListener {
    private lateinit var editText: WeakReference<CustomEditText>
    private lateinit var keyboardView: WeakReference<CustomKeyBoardView>

    fun bindEditText(et: CustomEditText) {
        editText = WeakReference(et)
        editText.get()?.setOnTextInputListener(this)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        //去掉dialog的标题，需要在setContentView()之前
        getDialog()?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        getDialog()?.setCanceledOnTouchOutside(false)
        val view: View = inflater.inflate(R.layout.fragment_dialog_keyboard, null)

        keyboardView =
            WeakReference(view.findViewById(R.id.key_board))
        keyboardView.get()?.setOnKeyListener(object : CustomKeyBoardView.OnKeyListener {

            override fun onInput(text: String?) {
                Log.d(TAG, "onInput: ${text}")
                editText.get()?.append(text)
            }


            override fun onDelete() {
                Log.d(TAG, "onDelete: ")
                val content: String = editText.get()?.getText().toString()
                if (content.length > 0) {
                    editText.get()?.setText(content.substring(0, content.length - 1))
                }
            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        val dialog: Dialog? = getDialog()
        dialog?.setCancelable(true);
        dialog?.setCanceledOnTouchOutside(true);

        if (dialog != null) {
            val window: Window? = dialog.getWindow()
            if (window != null) {
                val lp: WindowManager.LayoutParams = window.getAttributes()
                lp.windowAnimations = 1
                lp.dimAmount = 0F
                lp.width = 620
                //设置dialog的位置在底部
                lp.gravity = Gravity.BOTTOM

                window.setAttributes(lp)
            }
            //去掉dialog默认的padding
            window?.getDecorView()?.setPadding(0, 0, 0, 0);
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun dismiss() {
        super.dismiss()
        Log.i(TAG, "dismiss")
    }

    companion object {
        private const val TAG = "CustomKeyBoardFagment"
    }

    override fun onComplete(result: String?) {
        Log.i(TAG, "onComplete")
    }


}