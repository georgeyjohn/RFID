/**
 * LICENSE
 * This Source Code and its associated files are
 * Copyright 2017 Alien Technology LLC. All rights reserved.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package alien.com.wedge_app;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.alien.common.KeyCode;
import com.alien.rfid.RFID;
import com.alien.rfid.RFIDReader;
import com.alien.rfid.RFIDResult;
import com.alien.rfid.ReaderException;
import com.alien.rfid.Tag;

/**
 * Created by dmeng on 2/10/2017.
 */
public class BackgroundService extends AccessibilityService {
    private RFIDReader reader;
    private RFIDResult result;
    private String r = "Sample ";
    private int keyCodeValue;


    @Override
    public boolean onKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        Intent newIntent = new Intent(getApplicationContext(), WebActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);


        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyCode.ALR_H450.SCAN) {
                Log.e("-SCAN KEY UP Event-", "----------");
                // Display the tag data
                //  String r;
                keyCodeValue = 139;

                if (result.isSuccess()) {
                    Tag tag = (Tag) result.getData();
                    r = "EPC: " + tag.getEPC() + "\n" + "PC: " + tag.getPC() + "\n" + "RSSI: " + tag.getRSSI();


                } else {
                    r = "No tag found.";
                }

                Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG).show();
                return true;
            } else if (keyCode == KeyCode.ALR_H450.SIDE_LEFT) {
                Log.e("-LEFT KEY UP Event-", "----------");
                return true;
            } else {
                return super.onKeyEvent(event);
            }
        } else if (action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyCode.ALR_H450.SCAN) {
                Log.e("-SCAN KEY DOWN Event-", "----------");
                // read a tag
                try {
                    reader = RFID.open();
                    result = reader.read();
                } catch (ReaderException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (keyCode == KeyCode.ALR_H450.SIDE_LEFT) {
                Log.e("-LEFT KEY DOWN Event-", "----------");
                return true;
            } else {
                return super.onKeyEvent(event);
            }
        } else {
            return super.onKeyEvent(event);
        }
    }

    @Override
    public void onServiceConnected() {
        Log.e("-onServiceConnected-", "----------");


    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("-onAccessibilityEvent-", "----------");
        // AccessibilityNodeInfo source = event.getSource();
        //if (source != null && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && event.getClassName().equals("android.widget.EditText")) {
       /* if (event.getEventType()==AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", "TEST DATA");
            clipboard.setPrimaryClip(clip);
            source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT);
           // source.performAction(AccessibilityNodeInfo.ACTION_PASTE);
        }
*/
       /* Bundle bundle = new Bundle();

        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
        bundle.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                true);

        source.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
                bundle);

        ClipboardManager clipboard = (ClipboardManager) getBaseContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", "Test");
        clipboard.setPrimaryClip(clip);

        source.performAction(AccessibilityNodeInfo.ACTION_PASTE);*/


        final AccessibilityNodeInfo source1 = event.getSource();
        if (source1 == null) {
            return;
        }



   /*     try {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            String webViewTitle = searchWebView(root);
            String webViewURL = null;
            if (webViewTitle != null) {
                List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar");
                if (nodes.isEmpty()) {
                    nodes = root.findAccessibilityNodeInfosByViewId("com.android.browser:id/url");
                }
                for (AccessibilityNodeInfo node : nodes)
                    if (node.getText() != null) {
                        try {
                            webViewURL = new URL(node.getText().toString()).getHost();
                        } catch (MalformedURLException e) {
                            if (e.toString().contains("Protocol not found")) {
                                try {
                                    webViewURL = new URL("http://" + node.getText().toString()).getHost();
                                } catch (MalformedURLException ignored) {
                                }
                            }
                        }
                    }
            }
        } catch (Exception e) {
            // sadly we were unable to access the data we wanted
            return;
        }*/


        //     if (source != null && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {

        //  if (source.getPackageName() == "com.android.chrome") {
        if (/*keyCodeValue == 139*/true) {
            AccessibilityNodeInfo source2 = event.getSource();

            //  AccessibilityNodeInfoCompat node =getCursorOrInputCursor
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", r);
            clipboard.setPrimaryClip(clip);
            source2.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            keyCodeValue = 0;
        }
        //     }


    }


    //}
    private String searchWebView(AccessibilityNodeInfo source) {
        return searchWebView(source, 10);
    }

    private String searchWebView(AccessibilityNodeInfo source, int depth) {
        if (source == null || depth == 0) {
            return null;
        }
        for (int i = 0; i < source.getChildCount(); i++) {
            AccessibilityNodeInfo u = source.getChild(i);
            if (u == null) {
                continue;
            }
            if (u.getClassName() != null && u.getClassName().equals("android.webkit.WebView")) {
                if (u.getContentDescription() != null) {
                    return u.getContentDescription().toString();
                }
                return "";
            }
            String webView = searchWebView(u, depth - 1);
            if (webView != null) {
                return webView;
            }
            u.recycle();
        }
        return null;
    }

    @Override
    public void onInterrupt() {
    }
}
