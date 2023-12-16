package org.kde.kdeconnect.Plugins.MousePadPlugin;

import android.util.Log;

import org.apache.commons.net.telnet.TelnetClient;

import androidx.annotation.NonNull;

import org.kde.kdeconnect.Device;
import org.kde.kdeconnect.async.BackgroundJob;

import java.io.OutputStream;

public class DenonAvrCmdJob extends BackgroundJob<Device, Void> {
    final String receiverAddress;
    final String command;

    DenonAvrCmdJob(@NonNull Device device, @NonNull Callback<Void> callback, String receiverAddress, String command) {
        super(device, callback);
        this.receiverAddress = receiverAddress;
        this.command = command;
    }

    @Override
    public void run() {
        TelnetClient tc = new TelnetClient();
        try {
            tc.connect(this.receiverAddress, 23);
            OutputStream os = tc.getOutputStream();
            os.write(this.command.getBytes());
            os.write("\r".getBytes());
            os.flush();
            tc.disconnect();
        } catch (final Exception e) {
            Log.e("DenonAvrCmdJob", "Error", e);
        }
    }
}
