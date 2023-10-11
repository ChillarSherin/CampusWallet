package com.chillarcards.campuswallet.utils;

import android.content.Context;
import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;


/**
 * This is a thin veneer over EditText, with copy/paste/spell-check removed.
 */

public class NoMenuEditText extends TextInputEditText {
    private final Context context;

    public NoMenuEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NoMenuEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public NoMenuEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    /**
     * This is a replacement method for the base TextView class' method of the same name. This
     * method is used in hidden class android.widget.Editor to determine whether the PASTE/REPLACE popup
     * appears when triggered from the text insertion handle. Returning false forces this window
     * to never appear.
     *
     * @return false
     */
    boolean canPaste() {
        return false;
    }

    /**
     * This is a replacement method for the base TextView class' method of the same name. This method
     * is used in hidden class android.widget.Editor to determine whether the PASTE/REPLACE popup
     * appears when triggered from the text insertion handle. Returning false forces this window
     * to never appear.
     *
     * @return false
     */
    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    private void init() {
        this.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        this.setLongClickable(false);
    }

    @Override
    public int getSelectionStart() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getMethodName().equals("canPaste")) {
                return -1;
            }
        }
        return super.getSelectionStart();
    }

    /**
     * Prevents the action bar (top horizontal bar with cut, copy, paste, etc.) from appearing
     * by intercepting the callback that would cause it to be created, and returning false.
     */
    private class ActionModeCallbackInterceptor implements ActionMode.Callback {
        private final String TAG = NoMenuEditText.class.getSimpleName();

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}