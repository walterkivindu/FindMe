package com.example.findme;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddFound extends Activity {
	EditText name, from, dob, place, district, psch;
	ImageButton img;
	SQLiteDatabase db1;
	Uri targetUri;
	Bitmap bitmap;
	Button save;
	Thread thread;
	SqliteController1 db = new SqliteController1(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		thread = new Thread(runable);
		thread.start();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfound);

		save = (Button) findViewById(R.id.btnSave);
		name = (EditText) findViewById(R.id.txtname);
		from = (EditText) findViewById(R.id.txtFrom);
		dob = (EditText) findViewById(R.id.txtDate);
		place = (EditText) findViewById(R.id.txtPlace);
		district = (EditText) findViewById(R.id.txtDistrict);
		psch = (EditText) findViewById(R.id.txtPrimary);
		img = (ImageButton) findViewById(R.id.imageButton);

		registerForContextMenu(img);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching All products Activity

				Editable Name, From, DOB, Place, District, Psch;
				String nAme, doB, fRom, pLace, diStrict, pSch;
				// String Img;
				Name = name.getText();
				From = from.getText();
				DOB = dob.getText();
				Place = place.getText();
				District = district.getText();
				Psch = psch.getText();

				nAme = Name.toString();
				doB = DOB.toString();
				fRom = From.toString();
				pLace = Place.toString();
				diStrict = District.toString();
				pSch = Psch.toString();
				byte[] img1 = getImage();
				db.insertchild(nAme, fRom, doB, pLace, diStrict, pSch, img1);

				Toast.makeText(getApplicationContext(), "saved successfully",
						Toast.LENGTH_LONG).show();

				Intent i = new Intent(getApplicationContext(),
						ListFoundChild.class);
				startActivity(i);

			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select The Action");
		menu.add(0, v.getId(), 0, "Gallery");
		menu.add(0, v.getId(), 0, "Camera");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Camera") {
			try {
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File path = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				String name = getImageName();
				
				
				File fil = new File(path + "/FindMe/Found/");
				if (!fil.exists()) {
					if (!fil.mkdirs()) {
						Toast.makeText(getApplicationContext(),
								"Problem creating Image folder",
								Toast.LENGTH_LONG).show();
					}
				}
				
				File file = new File(path, "/FindMe/Found/" + name);
				Uri uri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, 0);
			} catch (Exception e) {
			}
		} else if (item.getTitle() == "Gallery") {
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, 1);
		} else {
			return false;
		}
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && null != data) {
			if (requestCode == 1) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				//cursor.close();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				Bitmap preview_bitmap = BitmapFactory.decodeFile(picturePath,
						options);

				File path = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				String name = getImageName();
				File fil = new File(path + "/FindMe/Found/");
				if (!fil.exists()) {
					if (!fil.mkdirs()) {
						Toast.makeText(getApplicationContext(),
								"Problem creating Image folder",
								Toast.LENGTH_LONG).show();
					}
				}
				FileOutputStream out;
				try {

					File file = new File(path, "/FindMe/Found/" + name);
					// file.createNewFile();
					out = new FileOutputStream(file);
					int bytes = preview_bitmap.getByteCount();

					ByteBuffer buffer = ByteBuffer.allocate(bytes);
					preview_bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
						out);
					preview_bitmap.copyPixelsToBuffer(buffer);
					byte[] array = buffer.array();// new byte[
					out.write(array);
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				img.setImageBitmap(preview_bitmap);
			}
			if (requestCode == 0) {
				// Bitmap bp = (Bitmap) data.getExtras().get("data");
				// img.setImageBitmap(bp);
				setImage();
			}
		}
	}

	

	@Override
	public void onBackPressed() {
		// Display alert message when back button has been pressed
		backButtonHandler();
		return;
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddFound.this);
		// Setting Dialog Title
		alertDialog.setTitle("Exit?");
		// Setting Dialog Message
		alertDialog.setMessage("Leave this page!");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.confirm);
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						Intent intent;
						intent = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(intent);
						finish();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	protected void setImage() {
		FileInputStream file;
		try {
			String name = getImageName();
			file = new FileInputStream("/sdcard/Pictures/FindMe/Found/" + name);
			byte[] img1 = new byte[file.available()];
			file.read(img1);
			Bitmap bm = BitmapFactory.decodeByteArray(img1, 0, img1.length);
			img.setImageBitmap(bm);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	protected byte[] getImage() {
		FileInputStream file;
		try {
			String name = getImageName();
			/*File path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			File fil = new File(path + "/FindMe/Found/");*/
			file = new FileInputStream("/sdcard/Pictures/FindMe/Found/" + name);
			byte[] img1 = new byte[file.available()];
			file.read(img1);
			return img1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected String getImageName() {
		// TODO Auto-generated method stub
		String name1 = name.getText() + ".jpg";
		return name1;
	}

	public Runnable runable = new Runnable() {
		public void run() {
			try {

				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				startActivityForResult(new Intent(AddFound.this,
						BackgroundService.class), 0);
				finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

}
