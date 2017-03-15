package kitttn.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author kitttn
 */
public class MainActivity extends AppCompatActivity {
    private static final String TEXTS_SHOWN = "texts";
    private static final String DIALOG_ACTIVE = "active";
    @BindView(R.id.container) LinearLayout container;
    @BindView(R.id.deleteBtn) Button deleteBtn;

    private int currentTextsShown = 5;
    private boolean dialogIsShowing = false;
    private AlertDialog dialog;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(TEXTS_SHOWN))
                currentTextsShown = savedInstanceState.getInt(TEXTS_SHOWN);
            if (savedInstanceState.containsKey(DIALOG_ACTIVE) && savedInstanceState.getBoolean(DIALOG_ACTIVE))
                showDeletingDialog();
        }

        createTextViews(currentTextsShown);
        checkButtonState();
        deleteBtn.setOnClickListener(view -> showDeletingDialog());
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TEXTS_SHOWN, currentTextsShown);
        outState.putBoolean(DIALOG_ACTIVE, dialogIsShowing);
    }

    private void showDeletingDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        dialogIsShowing = true;

        dialog = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("By clicking OK you will remove one text view")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogIsShowing = false;
                    deleteTextView();
                })
                .setOnDismissListener(dialogInterface -> dialogIsShowing = false)
                .setOnCancelListener(dialogInterface -> dialogIsShowing = false)
                .show();
    }

    private void deleteTextView() {
        int last = container.getChildCount() - 1;
        if (last > 0) {
            container.removeViewAt(last);
            currentTextsShown -= 1;
        }
        checkButtonState();
    }

    private void checkButtonState() {
        deleteBtn.setEnabled(currentTextsShown != 0);
    }

    @Override protected void onDestroy() {
        deleteBtn.setOnClickListener(null);
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        super.onDestroy();
    }

    private void createTextViews(int toShow) {
        for (int i = 0; i < toShow; ++i) {
            TextView view = new TextView(this);
            view.setText(String.format("I am text #%d", i));
            container.addView(view);
        }
    }
}
