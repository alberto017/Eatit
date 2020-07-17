package com.example.sanchez.eatit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ReestablecerUsuario extends AppCompatActivity {

    private EditText edtRecuperarUsuario;
    private ProgressBar pgbRecuperarUsuario;
    private ViewGroup vgEmailContainer;
    private ImageView imgEmail;
    private TextView lblAviso;

    private Button btnRecuperarUsuario;
    private String emailPattern = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_usuario);

        edtRecuperarUsuario = findViewById(R.id.edtRecuperarUsuario);
        imgEmail = findViewById(R.id.imgEmail);
        lblAviso = findViewById(R.id.lblAviso);
        pgbRecuperarUsuario = findViewById(R.id.pgbRecuperarUsuario);
        btnRecuperarUsuario = findViewById(R.id.btnRecuperarUsuario);

        //Instanciamos la conexion con el autenticador
        firebaseAuth = FirebaseAuth.getInstance();

        edtRecuperarUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }//onCreate

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRecuperarUsuario:

                TransitionManager.beginDelayedTransition(vgEmailContainer);
                imgEmail.setVisibility(View.VISIBLE);
                pgbRecuperarUsuario.setVisibility(View.VISIBLE);
                btnRecuperarUsuario.setEnabled(false);
                btnRecuperarUsuario.setTextColor(Color.argb(50, 255, 255, 255));

                firebaseAuth.sendPasswordResetEmail(edtRecuperarUsuario.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {

                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, imgEmail.getWidth() / 2, imgEmail.getHeight() / 2);
                                    scaleAnimation.setDuration(100);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            pgbRecuperarUsuario.setVisibility(View.GONE);
                                            lblAviso.setText("Coreo enviado satisfactoriamente!");
                                            lblAviso.setTextColor(getResources().getColor(R.color.successGreen));
                                            TransitionManager.beginDelayedTransition(vgEmailContainer);
                                            lblAviso.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                            imgEmail.setImageResource(R.drawable.email);
                                        }
                                    });
                                    imgEmail.startAnimation(scaleAnimation);
                                } else {
                                    String error = task.getException().getMessage();
                                    pgbRecuperarUsuario.setVisibility(View.GONE);
                                    lblAviso.setText(error);
                                    lblAviso.setTextColor(getResources().getColor(R.color.successFail));
                                    TransitionManager.beginDelayedTransition(vgEmailContainer);
                                    lblAviso.setVisibility(View.VISIBLE);
                                    //Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }//else
                                btnRecuperarUsuario.setEnabled(true);
                                btnRecuperarUsuario.setTextColor(Color.argb(255, 255, 255, 255));
                            }//else
                        });
                break;
        }
    }

    private void checkInputs() {
    }
}//ReestablecerUsuario