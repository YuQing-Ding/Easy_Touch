package com.EasySoftware.easytouch;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;

public class textwacher implements TextWatcher {

        private EditText editText;
        private TextWatcher watcher;
        private int byteLength;

        public textwacher(EditText editText, TextWatcher watcher, int byteLength) {
            this.editText = editText;
            this.watcher = watcher;
            this.byteLength = byteLength > 0 ? byteLength : 0;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (watcher != null) {
                watcher.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s != null) {
                String tmp = s.toString();
                try {
                    if (count > 0 && byteLength > 0) {
                        int cnt = count;
                        do {
                            tmp = s.toString().substring(0, start + cnt) + s.toString().substring(start + count);
                            byte[] bytes = tmp.getBytes("utf-8");
                            if (bytes.length < byteLength) {
                                break;
                            }
                        } while (cnt-- > 0);
                        if (!tmp.equals(s.toString())) {
                            editText.setText(tmp);
                            editText.setSelection(start + cnt);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (watcher != null) {
                watcher.onTextChanged(s, start, before, count);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (watcher != null) {
                watcher.afterTextChanged(s);
            }
        }
    }
