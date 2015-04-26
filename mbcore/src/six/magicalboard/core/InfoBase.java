package six.magicalboard.core;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;
@SuppressWarnings(value={"unchecked","rawtypes"})
public class InfoBase implements Parcelable{
	
	private int type = Constants.DRAWABLESKETCH;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	private int identifier = 0;
	
	public int getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	private HashMap<String,Object> entity;
	
	public HashMap<String, Object> getMyEntity() {
		return entity;
	}
	public InfoBase(){
		entity = new HashMap<String,Object>();
	}
	public InfoBase(int type,int id){
		this();
		this.type = type;
		this.identifier = id;
	}
	public int getInt(String key){
		if(null == key || !entity.containsKey(key)){
			return -1;
		}
		return (Integer)entity.get(key);
	}
	public void putInt(String key,int value){
		entity.put(key, value);
	}
	public boolean getBool(String key){
		if(null == key || !entity.containsKey(key)){
			return false;
		}
		return (Boolean)entity.get(key);
	}
	public void putBool(String key,boolean value){
		entity.put(key, value);
	}
	public String getString(String key){
		if(null == key || !entity.containsKey(key)){
			return null;
		}
		return (String)entity.get(key);
	}
	public void putString(String key,String value){
		entity.put(key, value);
	}
	
	public ArrayList getList(String key){
		if(null == key || !entity.containsKey(key)){
			return null;
		}
		return (ArrayList)entity.get(key);
	}
	public void putList(String key,ArrayList value){
		entity.put(key, value);
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(type);
		dest.writeInt(identifier);
		dest.writeMap(entity);
	}

	public static final Parcelable.Creator<InfoBase> CREATOR = new Creator<InfoBase>() {
		@Override
		public InfoBase createFromParcel(Parcel source) {
			InfoBase info = new InfoBase();
			info.type = source.readInt();
			info.identifier = source.readInt();
			info.entity = source.readHashMap(HashMap.class.getClassLoader());
			return info;
		}

		@Override
		public InfoBase[] newArray(int size) {
			// TODO Auto-generated method stub
			return new InfoBase[size];
		}
	};
}
