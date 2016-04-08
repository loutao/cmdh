package com.njucm.cmdh.app.CustomException;

import android.content.Context;

import com.njucm.cmdh.app.R;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mesogene on 3/21/15.
 */
public class ExceptionNetwork implements ErrorHandler{
    private Context context;

    public ExceptionNetwork(Context context) {
        this.context = context;
    }
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response response = cause.getResponse();

        if(response.getStatus() == 401) {
            try {
                return new Exception(this.context.getString(R.string.internete_nao_autorizado));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        if(response != null && response.getStatus() == 404) {
            try {
                return new Exception(this.context.getString(R.string.internete_nao_encontrado));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        if(response != null && response.getStatus() == 307)
        {
            try
            {
                return new Exception(this.context.getString(R.string.internete_link_redirecionado));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        if(response == null)
        {
            try {
                return new Exception(this.context.getString(R.string.internete_link_offiline));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return new Exception(this.context.getString(R.string.internete_link_offiline_other));
    }
}
