package com.example.sanchez.eatit;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanchez.eatit.Model.UsuarioModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private TextView lblTitle;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtPassword;
    private Button btnSignUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Enlazar controladores
        lblTitle = findViewById(R.id.lblTitle);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp_signUp);

        //Asignacion de la fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        lblTitle.setTypeface(typeface);

        //Conexion a firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "El telefono ya existe, intente con uno nuevo...", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            UsuarioModel usuarioModel = new UsuarioModel(edtName.getText().toString(), edtPhone.getText().toString(), edtPassword.getText().toString());
                            databaseReference.child(edtPhone.getText().toString()).setValue(usuarioModel);
                            Toast.makeText(SignUp.this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }//else
                    }//onDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }//onClick
        });
    }//onCreate
}//SignUp