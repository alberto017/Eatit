package com.example.sanchez.eatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanchez.eatit.Common.Common;
import com.example.sanchez.eatit.Model.UsuarioModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private TextView lblTitle;
    private EditText edtPhone;
    private EditText edtPassword;
    private Button btnSignIn;
    private TextView lblRecovery;
    private ImageView imgSignIn_signIn;

    private DatabaseReference databaseReference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Referencia de controladores
        lblTitle = findViewById(R.id.lblTitle_signIn);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn_signIn);
        lblRecovery = findViewById(R.id.lblRecovery_signIn);
        imgSignIn_signIn = findViewById(R.id.imgSignIn_signIn);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        lblTitle.setTypeface(typeface);

        //Conexion a firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (validaciones()) {
                            //Verificamos que exista el usiario posteriormente la contrasena
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                progressDialog.dismiss();
                                UsuarioModel usuarioModel = dataSnapshot.child(edtPhone.getText().toString()).getValue(UsuarioModel.class);
                                if (usuarioModel.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent maps01 = new Intent(SignIn.this, MenuLateral.class);
                                    Common.currentUsuarioModel = usuarioModel; //Obtenemos el usuario actual
                                    startActivity(maps01);
                                } else {
                                    Toast.makeText(SignIn.this, "¡Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                                }//else
                            } else {
                                Toast.makeText(SignIn.this, "¡Usuario incorecto!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }//else
                        } else {
                            progressDialog.dismiss();
                        }//else
                    }//onDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }//onCancelled
                });

            }
        });

    }//onCreate


    public boolean validaciones() {
        if (edtPhone.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignIn.this, "¡Existen campos vacios!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }//else
    }//validaciones

    public void onClick(View view) {
        switch (view.getId()) {
            ///case R.id.imgSignIn_signIn:
            case R.id.lblTitle:
                Toast.makeText(SignIn.this, "Ubicacion", Toast.LENGTH_LONG).show();

                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (validaciones()) {
                            //Verificamos que exista el usiario posteriormente la contrasena
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                progressDialog.dismiss();
                                UsuarioModel usuarioModel = dataSnapshot.child(edtPhone.getText().toString()).getValue(UsuarioModel.class);
                                if (usuarioModel.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent maps01 = new Intent(SignIn.this, Ubicacion.class);
                                    Common.currentUsuarioModel = usuarioModel; //Obtenemos el usuario actual
                                    startActivity(maps01);
                                } else {
                                    Toast.makeText(SignIn.this, "¡Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                                }//else
                            } else {
                                Toast.makeText(SignIn.this, "¡Usuario incorecto!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }//else
                        } else {
                            progressDialog.dismiss();
                        }//else
                    }//onDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }//onCancelled
                });
                break;

            case R.id.lblRecovery_signIn:
                Intent recovery = new Intent(SignIn.this, ReestablecerUsuario.class);
                startActivity(recovery);
                break;
        }//swtch
    }//onClick
}//SignIn