/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.documentsui;

import android.provider.DocumentsContract;

import com.android.documentsui.model.Document;
import com.android.internal.util.Predicate;

public class MimePredicate implements Predicate<Document> {
    private final String[] mFilters;

    public MimePredicate(String[] filters) {
        mFilters = filters;
    }

    @Override
    public boolean apply(Document doc) {
        if (DocumentsContract.MIME_TYPE_DIRECTORY.equals(doc.mimeType)) {
            return true;
        }
        for (String filter : mFilters) {
            if (mimeMatches(filter, doc.mimeType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean mimeMatches(String filter, String[] tests) {
        for (String test : tests) {
            if (mimeMatches(filter, test)) {
                return true;
            }
        }
        return false;
    }

    public static boolean mimeMatches(String filter, String test) {
        if (filter.equals(test)) {
            return true;
        } else if ("*/*".equals(filter)) {
            return true;
        } else if (filter.endsWith("/*")) {
            return filter.regionMatches(0, test, 0, filter.indexOf('/'));
        } else {
            return false;
        }
    }
}
