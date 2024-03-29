package br.com.lyrio.minhaagenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.lyrio.minhaagenda.R;
import br.com.lyrio.minhaagenda.dao.PessoaDAO;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);

        String telefone = sms.getDisplayOriginatingAddress();

        PessoaDAO dao = new PessoaDAO(context);
        if (dao.ehPessoa(telefone)) {
            Toast.makeText(context, "Chegou um SMS de uma pessoa da Minha Agenda", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
        dao.close();


    }
}
