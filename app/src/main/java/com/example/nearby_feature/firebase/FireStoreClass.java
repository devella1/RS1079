package com.example.nearby_feature.firebase;

import androidx.annotation.NonNull;

import com.example.nearby_feature.activities.Missing_banks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreClass {

    private FirebaseFirestore db;


    public static void addMissingBank(String bankName, String ifsc, String descripton, String address, String pincode, Boolean isAtm, Missing_banks activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> missingBank = new HashMap<>();
        missingBank.put("Name", bankName);
        missingBank.put("IFSC", ifsc);
        missingBank.put("Desc", descripton);
        missingBank.put("Addr", address);
        missingBank.put("Pincode", pincode);
        missingBank.put("isAtm", isAtm);



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

}
