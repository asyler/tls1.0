package com.example.asylertls.tls3;

/**
 * Created by asyler on 12/11/15.
 */

public class TLSContainerArray extends TLSContainer {
    private int _item_length;
    private int _length;
    private String className;

    TLSContainerArray(String name, int length, int item_length) {
        super(name, length);

        _length = length;
        _item_length = item_length;

    }

    @Override
    public void parse() {
        _length = data.length;
        setArray();
        super.parse();
    }

    public void removeItem(int position) {
        structure.remove(position);
    }

    public void setArray() {
        for (int i=0; i<_length/_item_length; i++)
            try {
                addParam(
                        ((TLSParameter2) Class.forName(BaseActivity.PATH+".fields."+toClassName(className)).newInstance())
                );
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void setArrayItem(String className) {
        this.className = className;
    }
}

