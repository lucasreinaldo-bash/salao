package vostore.salaoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tfb.fbtoast.FBToast;


import java.util.Arrays;

import config.ConfiguracaoFirebase;
import model.Preferencias;
import model.User;


public class Loginctivity extends AppCompatActivity  {


   //Create Objects
    private GoogleApiClient googleApiClient;
    private EditText editEmail, editPassword;
    private Button btnLoginFirebase, btnFacebookLogin, btnGoogle;
    private CallbackManager callbackManager;

    private TextView createAccount;
    private FirebaseAuth firebaseAuth, mFirebaseAuth;
    private static final String TAG = "SignInActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);





        LoginManager.getInstance().logOut();


        //Configurando o Firebase para recuperar a a sua instância
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();







        //Instanciando elementos do layout através dos seus indentificadores
        createAccount = findViewById(R.id.edit_create_account);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnLoginFirebase = findViewById(R.id.btn_firebase_login);
        btnFacebookLogin = findViewById(R.id.btn_facebook_login);







        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFacebook();
            }
        });


        //What will happen when the Create Account is triggered


    }





    //facebook login method
    private void loginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(Loginctivity.this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                //Log.d(Const.TAG_LOGIN_FACEBOOK, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                //Log.d(Const.TAG_LOGIN_FACEBOOK, "facebook:onError", error);
            }
        });
    }
    private void handleFacebookAccessToken ( final AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                  //          Log.e(Const.TAG_LOGIN_FACEBOOK, "Facebook error", task.getException());

                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            saveUser(currentUser, null);
                        } else
                            Toast.makeText(Loginctivity.this, "Authentication with Facebook failed.",
                                    Toast.LENGTH_SHORT).show();
                    }
                });
    }






    //Method for open Historic Activity
    private void abrirtela() {
        Intent intent = new Intent(Loginctivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    //Method of text Create Account .. Open this acitivity


    //Metódo para salvar o usuário em uma classe
    private void saveUser (FirebaseUser firebaseUser, String email){
        FBToast.successToast(Loginctivity.this,"Login efetuado com sucesso !",FBToast.LENGTH_SHORT);
        FirebaseUser currentUser = firebaseUser;
        Preferencias preferencias = new Preferencias(Loginctivity.this);
        preferencias.savePreferences(getString(R.string.id_user_app), firebaseUser.getUid());
        preferencias.savePreferences(getString(R.string.username_app), firebaseUser.getDisplayName());
        if (email != null) preferencias.savePreferences(getString(R.string.email_app), email);

        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setName(currentUser.getDisplayName());
        user.setAccountChecked(false);
        if (email != null)
            user.setEmail(email);
        user.saveUser();
        abrirtela();


    }









    @Override
    public void onStop() {
        super.onStop();
        // if (firebaseAuth.getCurrentUser() != null) openHistociScreen();


    }









}