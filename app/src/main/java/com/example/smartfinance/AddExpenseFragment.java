
package com.example.smartfinance;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartfinance.Database.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddExpenseFragment extends Fragment {
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int CAMERA_PERMISSION_CODE = 101;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    StorageReference mStorageReference;

    String userID, s, TransID;
    Date date;
    int selectedId;
    Double a;
    int lastChildPos;
    String currentPhotoPath;

    FloatingActionButton fab_confirm_expense,fab_cancel;
    EditText et_expense_amount, et_expense_date, et_expense_notes;
    Button btn_upload_receipt;
    ImageView img_receipt_expense;
    String category;
    Double amount;
    SessionManager sessionManager;

    RadioGroup radioGroupExpense;
    RadioButton btn_cat_salary, btn_cat_gift, radioButton;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int chosenMonth;
    private int chosenDay;
    private int chosenYear;
    static final int DATE_DIALOG_ID = 0;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        mFirestore = FirebaseFirestore.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        sessionManager = new SessionManager(getContext());

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        TransID = mFirestore.collection("AppointmentRecord").document().getId();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        fab_confirm_expense = view.findViewById(R.id.fab_confirm_add_expense);
        fab_cancel = view.findViewById(R.id.fab_cancel_add_expense);
        et_expense_amount = view.findViewById(R.id.et_amount_add_expense);
        et_expense_date = view.findViewById(R.id.et_date_add_expense);
        et_expense_notes = view.findViewById(R.id.et_notes_add_expense);
        btn_upload_receipt = view.findViewById(R.id.et_upload_add_expense);
        radioGroupExpense = view.findViewById(R.id.radio_group_expense);
        img_receipt_expense = view.findViewById(R.id.img_receipt_add_expense);


        radioGroupExpense.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId = radioGroupExpense.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.cat_food_drink_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_transport_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_house_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_bills_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_health_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_travel_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_entertainment_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_invest_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    case R.id.cat_others_expense:
                        radioButton = (RadioButton) view.findViewById(selectedId);
                        category = radioButton.getText().toString().trim();
                        break;
                    default:
                }

            }
        });

        et_expense_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(et_expense_date);
            }
        });

        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        btn_upload_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Choose Photo");
                passwordResetDialog.setMessage("Please choose to upload photo from camera or gallery");

                passwordResetDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askCameraPermissions();
                    }
                });
                passwordResetDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(openGalleryIntent, 1000);
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        fab_confirm_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastChildPos = radioGroupExpense.getChildCount() - 1;
                if (radioGroupExpense.getCheckedRadioButtonId() == -1) {
                    ((RadioButton) radioGroupExpense.getChildAt(lastChildPos)).setError("No category selected");
                    Toast.makeText(getContext(),"No category selected",Toast.LENGTH_SHORT).show();
                } else if (et_expense_amount.getText().toString().isEmpty()) {
                    et_expense_amount.setError("Please input amount");
                    et_expense_amount.requestFocus();
                } else if (et_expense_date.getText().toString().isEmpty()) {
                    et_expense_date.setError("Please input date");
                    Toast.makeText(getContext(),"No date selected",Toast.LENGTH_SHORT).show();
                    et_expense_date.requestFocus();
                } else {
                    s = et_expense_amount.getText().toString();
                    a = Double.valueOf(s.trim()).doubleValue();
                    DocumentReference documentReference = mFirestore.collection("Transaction Record")
                            .document(userID).collection("Expense").document(TransID);
                    final Map<String, Object> expense = new HashMap<>();
                    expense.put("TransactionID",TransID);
                    expense.put("TransactionAmount", et_expense_amount.getText().toString());
                    expense.put("TransactionCategory", category);
                    expense.put("TransactionDate", et_expense_date.getText().toString());
                    expense.put("TransactionDay", chosenDay);
                    expense.put("TransactionMonth", chosenMonth + 1);
                    expense.put("TransactionYear", chosenYear);
                    expense.put("TransactionTimeStamp", FieldValue.serverTimestamp());
                    expense.put("TransactionNotes", et_expense_notes.getText().toString());

                    documentReference.set(expense).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Expense is recorded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Record Expense Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        return view;
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {

            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getContext(), "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.smartfinance.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                chosenMonth = month;
                chosenDay = dayOfMonth;
                chosenYear = year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                img_receipt_expense.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                getContext().sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(), contentUri);


            }

        }
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
//                Uri ImageUri = data.getData();
//
//                uploadImageToFirebase(ImageUri);

                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
                img_receipt_expense.setImageURI(contentUri);

                uploadImageToFirebase(imageFileName, contentUri);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void uploadImageToFirebase(String name, Uri ImageUri) {
        final StorageReference fileRef = mStorageReference.child("receipt/" + mFirebaseAuth.getCurrentUser().getUid() + "/" + TransID + "/receipt.jpg");
        fileRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(img_receipt_expense);
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}