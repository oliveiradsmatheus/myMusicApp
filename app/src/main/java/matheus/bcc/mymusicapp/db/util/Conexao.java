package matheus.bcc.mymusicapp.db.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import matheus.bcc.mymusicapp.db.DBMySongs;
public class Conexao {
    private SQLiteDatabase database ;
    private DBMySongs sqliteOpenHelper ;

    public Conexao(Context context) {
       sqliteOpenHelper = new DBMySongs(context) ;
    }

    public void conectar() throws SQLException {
       database = sqliteOpenHelper.getWritableDatabase();
    }

    public void desconectar() {
       sqliteOpenHelper.close();
    }

    public int getMaxPK(String tabela, String chave) {
        Cursor cursor = database.rawQuery("select max("+chave+") from "+tabela,null);
        cursor.moveToFirst();
        int id=cursor.getInt(0);
        cursor.close();
        return id;
    }
    public long inserir(String tabela, ContentValues dados ) {
        return database.insert(tabela,null,dados);
    }

    public long alterar(String tabela, ContentValues dados, String restricao) {
        return database.update(tabela,dados,restricao,null);
    }

    public int apagar(String tabela, String query) {
        return database.delete(tabela , query, null);
    }

    public Cursor consultar(String query) {
        Cursor cursor = null;
        cursor = database.rawQuery(query, null);
        return cursor;
    }

}