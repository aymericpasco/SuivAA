package fr.pasco.aymeric.suivaa.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.pasco.aymeric.suivaa.R;
import fr.pasco.aymeric.suivaa.entities.Visit;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.VisitViewHolder> {

    private Context context;
    private List<Visit> visits;

    public VisitsAdapter(Context context, List<Visit> visits) {
        this.context = context;
        this.visits = visits;
    }

    @Override
    public VisitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VisitViewHolder(LayoutInflater.from(context).inflate(R.layout.visit_item, parent, false));
    }

    @Override
    public void onBindViewHolder(VisitViewHolder holder, int position) {
        holder.setVisitItem(visits.get(position));
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

    class VisitViewHolder extends RecyclerView.ViewHolder {

        //private TextView visitId;
        private TextView visitVisitDate;
        private TextView visitAppointment;
        private TextView visitDoctorFullname;

        VisitViewHolder(View visitItem) {
            super(visitItem);
            /*visitId = visitItem.findViewById(R.layout.visit_id);*/
            visitVisitDate = visitItem.findViewById(R.id.visit_visit_date);
            visitAppointment = visitItem.findViewById(R.id.visit_appointment);
            visitDoctorFullname = visitItem.findViewById(R.id.visit_doctor_fullname);
        }

        private void setVisitItem(Visit visit) {
            /*visitId.setText(visit.getId());*/
            visitVisitDate.setText(visit.getVisitDate());
            if (visit.getAppointment() == 1) {
                visitAppointment.setText("Sur rendez-vous");
            }
            visitDoctorFullname.setText(visit.getDoctor().getFullname());
        }

    }

}
