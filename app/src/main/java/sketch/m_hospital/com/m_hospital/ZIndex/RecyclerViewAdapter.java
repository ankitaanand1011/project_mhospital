package sketch.m_hospital.com.m_hospital.ZIndex;



/**
 * Created by ANDROID on 9/13/2017.
 */

public class RecyclerViewAdapter{

     /*   extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter {
    TextView tv;
    private List<String> mDataArray;

    public RecyclerViewAdapter(List<String> dataset) {
        mDataArray = dataset;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        tv = (TextView)v.findViewById(R.id.tv_alphabe);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // holder.mTextView = (TextView)
        holder.mTextView = tv;
        holder.mTextView.setText(mDataArray.get(position));
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        if (pos < 0 || pos >= mDataArray.size())
            return null;

        String name = mDataArray.get(pos);
        if (name == null || name.length() < 1)
            return null;

        return mDataArray.get(pos).substring(0, 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_alphabe)
        TextView mTextView ;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
*/
}
