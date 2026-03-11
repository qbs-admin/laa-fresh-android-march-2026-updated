package com.qbs.laafresh.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qbs.laafresh.data.network.api.reponse.AddressItem
import com.qbs.laafresh.databinding.ListItemAddressBinding


typealias addressItem = (addressItem: AddressItem) -> Unit
typealias addressOnSelected = (addressItem: AddressItem) -> Unit
typealias addressSelected = (id: String) -> Unit

class AddressListAdapter(var addressSelected: addressSelected) :
    RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {
    private val resAddressList = ArrayList<AddressItem>()
    private var addressId = ""

    inner class ViewHolder(private val binding: ListItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvCustomerAddress.text =
                    "${resAddressList[position].address1} ${resAddressList[position].address2}"
                tvAddressListDeliveryPhoneNumber.text = resAddressList[position].phone
                root.setOnClickListener {
                    addressSelected.invoke(resAddressList[position].id.toString())
                }

                /*ivRightIcon.setOnClickListener {
                    addressItem.invoke(resAddressList[position])
                }
                setOnClickListener {
                    addressOnSelected.invoke(resAddressList[position])
                }
                if(resAddressList[position].addressId==addressId){
                    tvCustomerAddress.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    tvAddressListDeliveryPhoneNumber.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
                }else{
                    tvCustomerAddress.setTextColor(ContextCompat.getColor(context,R.color.textHeader))
                    tvAddressListDeliveryPhoneNumber.setTextColor(ContextCompat.getColor(context,R.color.textHeader))
                }
                tvCustomerAddress.text =
                    resAddressList[position].firstname + " " + resAddressList[position].lastname + "\n" +resAddressList[position].address_1+"\n"+
                            resAddressList[position].address_2 + ",\n" + resAddressList[position].city+" "+resAddressList[position].postcode
                tvAddressListDeliveryPhoneNumber.text=resAddressList[position].delivery_phone_number*/
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = resAddressList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun listAddress(resAddress: List<AddressItem>) {
        resAddressList.clear()
        resAddressList.addAll(resAddress)
        notifyDataSetChanged()

    }

}