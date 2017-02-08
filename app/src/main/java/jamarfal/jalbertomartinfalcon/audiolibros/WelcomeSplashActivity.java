package jamarfal.jalbertomartinfalcon.audiolibros;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class WelcomeSplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_TIME = 1000;
    TextView splashLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);

        splashLabel = (TextView) findViewById(R.id.splash_label);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.loop_rotation);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent mainIntent = new Intent(WelcomeSplashActivity.this,
                        CustomLoginActivity.class);


                startActivity(mainIntent);
                     /* Finish splash activity so user cant go back to it. */
                WelcomeSplashActivity.this.finish();


                overridePendingTransition(R.anim.entrada_derecha, R.anim.salida_izquierda);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        splashLabel.startAnimation(anim);


//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//
//                Intent mainIntent = new Intent(WelcomeSplashActivity.this,
//                        MainActivity.class);
//
//
//                startActivity(mainIntent);
//                     /* Finish splash activity so user cant go back to it. */
//                WelcomeSplashActivity.this.finish();
//
//
//                overridePendingTransition(R.anim.entrada_derecha, R.anim.salida_izquierda);
//            }
//        }, SPLASH_DISPLAY_TIME);
    }
}
