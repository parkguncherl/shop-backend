-- Order/cart/payment-history adjustments
-- 1) Keep the source cart id on each order.
ALTER TABLE public.tb_order
    ADD COLUMN IF NOT EXISTS cart_id int8 NULL;

CREATE INDEX IF NOT EXISTS ix_tb_order_cart_id
    ON public.tb_order USING btree (cart_id);

-- 2) Speed up payment-history search by paid date.
CREATE INDEX IF NOT EXISTS ix_tb_payment_paid_tm
    ON public.tb_payment USING btree (paid_tm);

-- Optional one-time correction for already approved test orders.
-- Replace the order_no value before running.
-- UPDATE public.tb_order
-- SET order_status = 'P',
--     upt_tm = NOW()
-- WHERE order_no = 'ORD-XXXXXXXXXXXXX';
--
-- UPDATE public.tb_payment
-- SET payment_status = 'P',
--     paid_tm = COALESCE(paid_tm, NOW()),
--     upt_tm = NOW()
-- WHERE order_no = 'ORD-XXXXXXXXXXXXX'
--   AND del_yn = 'N';
--
-- UPDATE public.tb_payment_det
-- SET approved_tm = COALESCE(approved_tm, NOW()),
--     upt_tm = NOW()
-- WHERE order_no = 'ORD-XXXXXXXXXXXXX'
--   AND del_yn = 'N';
