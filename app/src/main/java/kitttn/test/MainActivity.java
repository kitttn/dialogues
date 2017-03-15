package kitttn.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @BindView(R.id.container) LinearLayout container;
    @BindView(R.id.deleteBtn) Button deleteBtn;
    private int currentTextsShown = 5;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(TEXTS_SHOWN))
            currentTextsShown = savedInstanceState.getInt(TEXTS_SHOWN);

        createTextViews(currentTextsShown);

        deleteBtn.setOnClickListener(view -> {});
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TEXTS_SHOWN, currentTextsShown);
    }

    @Override protected void onDestroy() {
        deleteBtn.setOnClickListener(null);
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
