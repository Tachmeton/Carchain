package de.dhbw.chaincar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.zxing.WriterException;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.dhbw.chaincar.adapter.AvailableVehicleAdapter;
import de.dhbw.chaincar.adapter.RentVehicleAdapter;
import de.dhbw.chaincar.data.RentVehicle;
import de.dhbw.chaincar.data.Vehicle;

import static android.content.Context.WINDOW_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeyList extends Fragment {

    View view;

    private ListView keyListView;
    private ProgressBar vehicleListLoadingIndicator;
    private String renterAddress = "0x3d48704143135059A1990dcDF9eEC5C73f750179";


    public KeyList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_key_list, container, false);

        keyListView = view.findViewById(R.id.keyList);
        vehicleListLoadingIndicator = view.findViewById(R.id.listLoadingIndicator);

        final ArrayList<RentVehicle> rentVehicles;
        try {
            // vehicles = VehicleLoader.getInstance(getContext()).getVehicles();
            rentVehicles = new ArrayList<>();

            // TODO: Fetch rent vehicles from Blockchain
            Vehicle vehicle = new Vehicle("1", "VW Golf 7 GTI",
                    "Deutschland", "Reutlingen", "72764",
                    "Musterstraße", "1", "RT-VS-2020",
                    "Golf 7 GTI", "Kombi", "VW",
                    "schwarz", 245,
                    17f, 60, 480);
            rentVehicles.add(new RentVehicle(168, vehicle));

            RentVehicleAdapter vehicleAdapter = new RentVehicleAdapter(view.getContext(), rentVehicles);
            keyListView.setAdapter(vehicleAdapter);
            vehicleListLoadingIndicator.setVisibility(View.GONE);

            keyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;
                    QRGEncoder qrgEncoder = new QRGEncoder(renterAddress, null, QRGContents.Type.TEXT, smallerDimension);

                    try {
                        Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                        AlertDialog.Builder alertadd = new AlertDialog.Builder(getContext());
                        final View alertView = inflater.inflate(R.layout.qr_dialog, null);
                        ((ImageView)alertView.findViewById(R.id.qr)).setImageBitmap(bitmap);
                        alertadd.setView(alertView);
                        alertadd.setNeutralButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {

                            }
                        });

                        alertadd.show();
                    } catch (WriterException e) {
                        AlertDialog.Builder alertfail = new AlertDialog.Builder(getContext()).setMessage("Schlüssel konnte nicht erzeugt werden").setTitle("Fehler");
                        alertfail.setNeutralButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertfail.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
