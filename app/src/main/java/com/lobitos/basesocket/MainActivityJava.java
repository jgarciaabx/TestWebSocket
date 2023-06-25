package com.lobitos.basesocket;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivityJava extends AppCompatActivity {

    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        wss://ws.sib.mobile.abexacloud.com/ws?codDispositivo=31&codTipoDispositivo=10&codEmpresa=25&imei=1245698741&telefono=99999999&idConexion=452&v=sibv12.0.1.sdk2
//        ws://161.132.99.147:2029/ws?codDispositivo=31&codTipoDispositivo=10&codEmpresa=25&imei=1245698741&telefono=99999999&idConexion=452&v=sibv12.0.1.sdk2

        String url = "wss://ws.sib.mobile.abexacloud.com/ws?codDispositivo=31&codTipoDispositivo=10&codEmpresa=25&imei=1245698741&telefono=99999999&idConexion=452&v=sibv12.0.1.sdk2";
        Log.i("WebSocket", "URL !!: " + url);
        connectWebSocket(url);
    }

    private void connectWebSocket(String url) {
        try {
            URI uri = new URI(url);
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.i("WebSocket", "Conexión WebSocket establecida con éxito");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // La conexión WebSocket se ha abierto
                            Log.i("WebSocket", "Mensaje recibido: OPEN ");
                        }
                    });
                }

                @Override
                public void onMessage(String message) {
                    Log.i("WebSocket", "Mensaje recibido1 : " + message);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Se ha recibido un mensaje
                            Log.i("WebSocket", "Mensaje recibido: " + message);
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // La conexión WebSocket se ha cerrado
                            Log.i("WebSocket", "Mensaje recibido: CLOSE ");
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    Log.i("WebSocket", "Error al establecer conexión WebSocket: " + ex.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Se ha producido un error en la conexión WebSocket
                            Log.i("WebSocket", "Mensaje recibido: ERROR ");
                        }
                    });
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void sendWebSocketMessage(String message) {
        if (webSocketClient != null) {
            webSocketClient.send(message);
        }
    }

    private void closeWebSocketConnection() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
