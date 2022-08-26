package com.example.nearby_feature.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nearby_feature.activities.Missing_banks;
import com.example.nearby_feature.newPlace;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreClass {

    private FirebaseFirestore db;



    public static void addMissingBank(String Name, String descripton, String latitude, String longitude, String type, Missing_banks activity, boolean isAdmin) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> missingBank = new HashMap<>();
        missingBank.put("Name", Name);
        missingBank.put("Desc", descripton);
        missingBank.put("lat", latitude);
        missingBank.put("lon", longitude);





        if(isAdmin){
            db.collection(type)
                    .add( missingBank)
                    .addOnSuccessListener(
                            activity.addSuccess()
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });
        }
        else{
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


    public static String getCurrentUserID() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String currentUserID = "";

        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
    }

    public static  List<newPlace> getNearBy(){
//        CollectionReference DsRef = db.collection("dataset");
//        //Query query = citiesRef.whereEqualTo("state", "CA");
        List<newPlace> ds = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dataset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                newPlace np =document.toObject(newPlace.class);
//                                ds.add(np);

                                newPlace np =new newPlace("hello","31.25809249169579","75.70791196078062","24/7","Atm");

                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Log.d("TAG", np.desc);
                            }
                        } else {

                        }
                    }
                });
        Log.d("TAG", String.valueOf(ds.size()));
        return ds;
    }
    
    
    public static void addToFav(String currentUserID ){
        FirebaseFirestore db = FirebaseFirestore.getInstance();


//        db.collection("favs").doc()
//                .get()
//                .then(function(doc) {
//            if (doc.exists) {
//                console.log("Document data:", doc.data());
//            } else {
//                // doc.data() will be undefined in this case
//                console.log("No such document!");
//            }
//        }).catch(function(error) {
//            console.log("Error getting document:", error);
//        });

        db.collection("favs")
                .whereEqualTo("userId", currentUserID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        }

                    }
                });
    }



}
