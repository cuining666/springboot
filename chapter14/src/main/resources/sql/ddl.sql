create table t_product(
                          id int(12) not null auto_increment comment '编号',
                          product_name varchar(60) not null comment '产品名称',
                          stock int(10) not null comment '库存',
                          price decimal(16,2) not null comment '单价',
                          version int(10) not null default 0 comment '版本',
                          note varchar(256) null comment '备注',
                          primary key(id)
);

create table t_purchase_record(
                                  id int(12) not null auto_increment comment '编号',
                                  user_id int(12) not null comment '用户编号',
                                  product_id int(12) not null comment '产品编号',
                                  price decimal(16,2) not null comment '价格',
                                  quantity int(12) not null comment '数量',
                                  sum decimal(16,2) not null comment '总价',
                                  purchase_date timestamp not null default now() comment '购买时间',
                                  primary key(id)
);