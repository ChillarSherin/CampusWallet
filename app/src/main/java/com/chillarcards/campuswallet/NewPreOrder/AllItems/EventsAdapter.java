package com.chillarcards.campuswallet.NewPreOrder.AllItems;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.chillarcards.campuswallet.R;

import java.util.Collections;
import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.CartListActivity;
import com.chillarcards.campuswallet.NewPreOrder.DummyOrderItems;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.networkmodels.OrderItemsDetails.Item;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Dialog OtpDialoglogout;
	Context mContext;
	Activity activity;
	CartCallback cartCallback;

	private static class HeaderViewHolder extends RecyclerView.ViewHolder {

		TextView txt_header;

		HeaderViewHolder(View itemView) {
			super(itemView);
			txt_header = (TextView) itemView.findViewById(R.id.txt_header);
		}

	}

	private static class EventViewHolder extends RecyclerView.ViewHolder {

		TextView TeacherSubnameTV,ToteacherMessageStatusTV,ToteacherMessageTV,ToteacherMessageDateTV;

		EventViewHolder(View itemView) {
			super(itemView);
			TeacherSubnameTV=itemView.findViewById(R.id.CartItemNameTV);
			ToteacherMessageStatusTV=itemView.findViewById(R.id.QtyEBTN);
			ToteacherMessageTV=itemView.findViewById(R.id.CartOutletNameTV);
			ToteacherMessageDateTV=itemView.findViewById(R.id.ItemPriceTV);
		}

	}

	@NonNull
	private List<ListItem> items = Collections.emptyList();

	public EventsAdapter(@NonNull List<ListItem> items, Context mContext,Activity activity,CartCallback cartCallback) {
		this.items = items;
		this.mContext = mContext;
		this.activity = activity;
		this.cartCallback = cartCallback;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case ListItem.TYPE_HEADER: {
				View itemView = inflater.inflate(R.layout.view_list_item_header, parent, false);
				return new HeaderViewHolder(itemView);
			}
			case ListItem.TYPE_EVENT: {
				View itemView = inflater.inflate(R.layout.row_new_preorder_dummy, parent, false);
				return new EventViewHolder(itemView);
			}
			default:
				throw new IllegalStateException("unsupported item type");
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
		int viewType = getItemViewType(position);
		switch (viewType) {
			case ListItem.TYPE_HEADER: {
				HeaderItem header = (HeaderItem) items.get(position);
				HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
				// your logic here
				holder.txt_header.setText(header.getSession());
				break;
			}
			case ListItem.TYPE_EVENT: {
				EventItem event = (EventItem) items.get(position);
				EventViewHolder holder = (EventViewHolder) viewHolder;
				// your logic here
				holder.TeacherSubnameTV.setText(event.getEvent().getTitle().getItemName());
				holder.ToteacherMessageTV.setText("Outlet : "+event.getEvent().getTitle().getOutletName());
				holder.ToteacherMessageStatusTV.setText("Price : "+mContext.getResources().getString(R.string.indian_rupee_symbol)+event.getEvent().getTitle().getItemGSTPrice());
				holder.ToteacherMessageDateTV.setText("Quantity : "+event.getEvent().getTitle().getItemAvailability());
				break;
			}
			default:
				throw new IllegalStateException("unsupported item type");
		}
		if (viewType==ListItem.TYPE_EVENT) {
			EventViewHolder eventViewHolder = (EventViewHolder) viewHolder;
			eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					EventItem event = (EventItem) items.get(position);
					AddtoCartPopup(event.getEvent().getTitle());
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).getType();
	}
	public void AddtoCartPopup(final Item event)
	{
		OtpDialoglogout = new Dialog(mContext);
		OtpDialoglogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
		OtpDialoglogout.setContentView(R.layout.food_item_addtocart_popup);
		OtpDialoglogout.setCanceledOnTouchOutside(true);
		OtpDialoglogout.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		OtpDialoglogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		TextView ItemNameTV = (TextView) OtpDialoglogout.findViewById(R.id.ItemNameTV);
		final TextView ItemPriceTV = (TextView) OtpDialoglogout.findViewById(R.id.ItemPriceTV);
		final TextView ErrorItemQtyTV = (TextView) OtpDialoglogout.findViewById(R.id.ErrorItemQtyTV);
		final ElegantNumberButton number_button=(ElegantNumberButton) OtpDialoglogout.findViewById(R.id.number_button);
		TextView CheckOutBTN=(TextView) OtpDialoglogout.findViewById(R.id.CheckOutBTN);
		TextView AddtoCartBTN=(TextView) OtpDialoglogout.findViewById(R.id.AddtoCartBTN);
		final TextView ItemPriceHiddenTV=(TextView) OtpDialoglogout.findViewById(R.id.ItemPriceHiddenTV);
		ErrorItemQtyTV.setVisibility(View.GONE);
		ItemNameTV.setText(event.getItemName());
		ItemPriceTV.setText("Price : "+mContext.getResources().getString(R.string.indian_rupee_symbol)+event.getItemGSTPrice());
		number_button.setRange(1,Integer.parseInt(event.getItemAvailability()));

		final String TotalAmount=event.getItemGSTPrice();
		ItemPriceHiddenTV.setText(event.getItemGSTPrice());
		String TotalQty=event.getItemAvailability();


		if (Constants.CartItems.size() != 0) {
			for (int i = 0; i < Constants.CartItems.size(); i++) {
				if (Constants.CartItems.get(i).getId().equalsIgnoreCase(event.getItemID())) {

					number_button.setNumber(Constants.CartItems.get(i).getQty());
				}
				else {
					number_button.setNumber("1");
				}
			}
		}
		else {

			number_button.setNumber("1");
		}


		number_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
			@Override
			public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

				float total=(Float.parseFloat(event.getItemGSTPrice()))*newValue;
				ItemPriceHiddenTV.setText(String.valueOf(total));
				ItemPriceTV.setText("Price : "+mContext.getResources().getString(R.string.indian_rupee_symbol)+total);
				if (newValue>=Integer.parseInt(event.getItemAvailability()))
				{
					ErrorItemQtyTV.setText("Available quantity is "+event.getItemAvailability()+", You are not allowed to add more than that.");
					ErrorItemQtyTV.setVisibility(View.VISIBLE);
				}
				else
				{
					ErrorItemQtyTV.setVisibility(View.GONE);
				}
			}
		});

		try {
			OtpDialoglogout.show();
		}catch (Exception e){
			//System.out.println("Logout PopUp Error : "+ e.toString());
		}
		CheckOutBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				boolean isAdded=false;
				if (Integer.parseInt(number_button.getNumber())>0) {
					if (Constants.CartItems.size() != 0) {
						for (int i = 0; i < Constants.CartItems.size(); i++) {
							if (Constants.CartItems.get(i).getId().equalsIgnoreCase(event.getItemID())) {
								Constants.CartItems.set(i, new DummyOrderItems(event.getItemID(),
										event.getItemName(),
										event.getItemName(),
										ItemPriceHiddenTV.getText().toString(),
										event.getOrderCategoryID(),
										event.getOutletName(),
										String.valueOf(/*Integer.parseInt(Constants.CartItems.get(i).getQty())+*/Integer.parseInt(number_button.getNumber())),
										event.getItemGSTPrice(),
										event.getItemAvailability(),
										event.getOutletConfigID(),
										event.getOrderSessionTimingID()));

								//System.out.println("Total ITEM QTY : "+String.valueOf(/*Integer.parseInt(Constants.CartItems.get(i).getQty())+*/Integer.parseInt(number_button.getNumber())));
								isAdded = true;
								break;
							} else {
								isAdded = false;
							}
						}
						if (!isAdded) {
							Constants.CartItems.add(new DummyOrderItems(event.getItemID(),
									event.getItemName(),
									event.getItemName(),
									ItemPriceHiddenTV.getText().toString(),
									event.getOrderCategoryID(),
									event.getOutletName(),
									number_button.getNumber(),
									event.getItemGSTPrice(),
									event.getItemAvailability()
									,event.getOutletConfigID(),
									event.getOrderSessionTimingID()));
						}
					} else {
						Constants.CartItems.add(new DummyOrderItems(event.getItemID(),
								event.getItemName(),
								event.getItemName(),
								ItemPriceHiddenTV.getText().toString(),
								event.getOrderCategoryID(),
								event.getOutletName(),
								number_button.getNumber(),
								event.getItemGSTPrice(),
								event.getItemAvailability()
								,event.getOutletConfigID(),
								event.getOrderSessionTimingID()));
					}
//				cartCallback.onAddtocartCallback();

					OtpDialoglogout.dismiss();
					Intent i = new Intent(mContext, CartListActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(i);
//					((Activity) mContext).finish();
				}

			}
		});
		AddtoCartBTN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isAdded=false;
				if (Integer.parseInt(number_button.getNumber())>0) {
					if (Constants.CartItems.size() != 0) {
						for (int i = 0; i < Constants.CartItems.size(); i++) {
							if (Constants.CartItems.get(i).getId().equalsIgnoreCase(event.getItemID())) {
								Constants.CartItems.set(i, new DummyOrderItems(event.getItemID(),
										event.getItemName(),
										event.getItemName(),
										ItemPriceHiddenTV.getText().toString(),
										event.getOrderCategoryID(),
										event.getOutletName(),
										String.valueOf(/*Integer.parseInt(Constants.CartItems.get(i).getQty())+*/Integer.parseInt(number_button.getNumber())),
										event.getItemGSTPrice(),
										event.getItemAvailability(),
										event.getOutletConfigID(),
										event.getOrderSessionTimingID()));

								//System.out.println("Total ITEM QTY : "+String.valueOf(/*Integer.parseInt(Constants.CartItems.get(i).getQty())+*/Integer.parseInt(number_button.getNumber())));
								isAdded = true;
								break;
							} else {
								isAdded = false;
							}
						}
						if (!isAdded) {
							Constants.CartItems.add(new DummyOrderItems(event.getItemID(),
									event.getItemName(),
									event.getItemName(),
									ItemPriceHiddenTV.getText().toString(),
									event.getOrderCategoryID(),
									event.getOutletName(),
									number_button.getNumber(),
									event.getItemGSTPrice(),
									event.getItemAvailability()
									,event.getOutletConfigID(),
									event.getOrderSessionTimingID()));
						}
					} else {
						Constants.CartItems.add(new DummyOrderItems(event.getItemID(),
								event.getItemName(),
								event.getItemName(),
								ItemPriceHiddenTV.getText().toString(),
								event.getOrderCategoryID(),
								event.getOutletName(),
								number_button.getNumber(),
								event.getItemGSTPrice(),
								event.getItemAvailability()
								,event.getOutletConfigID(),
								event.getOrderSessionTimingID()));
					}
					cartCallback.onAddtocartCallback();
					OtpDialoglogout.dismiss();
				}


			}
		});

	}

}
