package com.bepi.ugandroid.ui.common

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.bepi.ugandroid.R
import com.bepi.ugandroid.ui.common.CustomKeyBoardView


/**
 * Created by jhn on 2018/9/20.
 * Description :
 */
class CustomKeyboardDialogFragment : DialogFragment() {


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
//        val exitImgView: ImageView = view.findViewById(R.id.iv_exit)
//        exitImgView.setOnClickListener(object : DialogInterface.OnClickListener() {
//            fun onClick(v: View?) {
//                this@C.dismiss()
//            }
//        })
//        val editText: PwdEditText = view.findViewById(R.id.et_input)
//        editText.setOnTextInputListener(this)
        val keyboardView: CustomKeyBoardView =
            view.findViewById(R.id.key_board) as CustomKeyBoardView
        keyboardView.setOnKeyListener(object : CustomKeyBoardView.OnKeyListener {

            override fun onInput(text: String?) {
//                TODO("Not yet implemented")
                Log.d(TAG, "onInput: ${text}")
            }


            override fun onDelete() {
                Log.d(TAG, "onDelete: ")
//                val content: String = editText.getText().toString()
//                if (content.length > 0) {
//                    editText.setText(content.substring(0, content.length - 1))
//                }
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

                window?.setAttributes(lp)
            }
            //去掉dialog默认的padding
            window?.getDecorView()?.setPadding(0, 0, 0, 0);
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }

    fun onComplete(result: String) {
        Log.d(TAG, "onComplete: result = $result")
        Toast.makeText(getContext(), "input complete : $result", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "PayDialogFragment"
    }
}