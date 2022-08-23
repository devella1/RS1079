package com.example.nearby_feature.firebase;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nearby_feature.activities.Missing_banks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreClass {

    private FirebaseFirestore db;


    public static void addMissingBank(String Name,  String descripton, String latitude, String longitude, String type, Missing_banks activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> missingBank = new HashMap<>();
        missingBank.put("Name", Name);
        missingBank.put("Desc", descripton);
        missingBank.put("lat", latitude);
        missingBank.put("lon", longitude);
        missingBank.put("type", type);



        db.collection("missingBanks")
                .add( missingBank)
                .addOnSuccessListener(
                        activity.addSuccess()
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });

    }


    public static String getCurrentUserID() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUserID = "";

        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
    }

}
