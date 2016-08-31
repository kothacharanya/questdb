/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2016 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.ql.model;

public class CreateAsSelectModel {
    private final String name;
    private final QueryModel queryModel;
    private ExprNode timestamp;
    private ExprNode partitionBy;
    private ExprNode recordHint;

    public CreateAsSelectModel(String name, QueryModel queryModel) {
        this.name = name;
        this.queryModel = queryModel;
    }

    public String getName() {
        return name;
    }

    public ExprNode getPartitionBy() {
        return partitionBy;
    }

    public void setPartitionBy(ExprNode partitionBy) {
        this.partitionBy = partitionBy;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public ExprNode getRecordHint() {
        return recordHint;
    }

    public void setRecordHint(ExprNode recordHint) {
        this.recordHint = recordHint;
    }

    public ExprNode getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ExprNode timestamp) {
        this.timestamp = timestamp;
    }
}
