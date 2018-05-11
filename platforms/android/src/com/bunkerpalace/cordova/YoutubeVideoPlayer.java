package com.bunkerpalace.cordova;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

//Importa pacotes para criar a intent do youtube
import java.lang.Object;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.keyes.youtube.OpenYouTubePlayerActivity;

public class YoutubeVideoPlayer extends CordovaPlugin {

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if(action.equals("openVideo")) {
			String url = args.getString(0);
        	this.openVideo(url, callbackContext);
        	return true;
        }

		return false;
	}

	private void openVideo(String vid, CallbackContext callbackContext) {

		//Pega a app level do android
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		//Chama o service do youtube
		YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this.cordova.getActivity());
		//Se retornar sucesso
		if(result == YouTubeInitializationResult.SUCCESS)
		{
			//Cria a intent do youtube
			//o segundo parametro tem que alterar por uma APIKEY do app
			Intent intent = YouTubeStandalonePlayer.createVideoIntent(this.cordova.getActivity(), "AIzaSyDKUFw8Mm0nlIIMI09PMaLF0jZAV4M0-XE", vid, 0, true, false);
			this.cordova.getActivity().startActivity(intent);
		}
		else if(SDK_INT < 22) //app level 22 é equivalente a versão 5.1 do android
		{
			//Toast.makeText(this.cordova.getActivity(),"É necessario o youtube",Toast.LENGTH_SHORT).show();   Essa linha era pra mostrar um alerta
			Intent intent = new Intent(null, Uri.parse("ytv://"+vid), this.cordova.getActivity(), OpenYouTubePlayerActivity.class);
			this.cordova.getActivity().startActivity(intent);
		}
	}

}

//plugman install --platform android --project "C:\Users\Igor\Desktop\projetoVideoPlayer\platforms\android" --plugin "C:\Users\Igor\Desktop\projetoVideoPlayer\plugins\com.bunkerpalace.cordova.YoutubeVideoPlayer"