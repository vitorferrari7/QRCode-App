package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import net.glxn.qrgen.core.*;

import java.util.HashMap;
import java.util.Map;


public class GenerateQRCodeActivity extends AppCompatActivity {
    private TextView qrCodeTV;
    private ImageView qrCodeIV;
    private TextInputEditText dataEdt;
    private Button generateQRBtn;

    private QRCode qrCode;

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        qrCodeTV = findViewById(R.id.idTVGenerateQR);
        qrCodeIV = findViewById(R.id.idIVQRCode);
        dataEdt = findViewById(R.id.idEdtData);
        generateQRBtn = findViewById(R.id.idBtnGenerateQR);
        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dataEdt.getText().toString();
                if (data.isEmpty()) {
                    Toast.makeText(GenerateQRCodeActivity.this, "Please enter some data to Generate QR Code..", Toast.LENGTH_SHORT).show();
                } else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // Define as configurações do QR Code
                    Map<EncodeHintType, Object> hints = new HashMap<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

                    try {
                        // Gera o código QR a partir dos dados informados
                        BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, dimen, dimen, hints);

                        // Converte o bitMatrix em um Bitmap
                        int matrixWidth = bitMatrix.getWidth();
                        int matrixHeight = bitMatrix.getHeight();
                        Bitmap bmp = Bitmap.createBitmap(matrixWidth, matrixHeight, Bitmap.Config.RGB_565);
                        for (int x = 0; x < matrixWidth; x++) {
                            for (int y = 0; y < matrixHeight; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        // Exibe o código QR gerado
                        qrCodeTV.setVisibility(View.GONE);
                        qrCodeIV.setImageBitmap(bmp);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}