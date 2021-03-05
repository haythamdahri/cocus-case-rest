package com.cocus.microservices.cases.config;

import com.cocus.microservices.bo.entities.CaseBO;
import com.cocus.microservices.cases.clients.CustomerClient;
import com.cocus.microservices.cases.dto.CustomerDTO;
import com.cocus.microservices.cases.repositories.CaseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haytham DAHRI
 */
@Configuration
public class ApplicationRunner implements CommandLineRunner {

    private final CaseRepository caseRepository;
    private final CustomerClient customerClient;

    public ApplicationRunner(CaseRepository caseRepository, CustomerClient customerClient) {
        this.caseRepository = caseRepository;
        this.customerClient = customerClient;
    }

    /**
     * Populate Testing DATA if environment is not production
     *
     * @param args Input Arguments
     */
    @Override
    public void run(String... args) {
        if (this.caseRepository.count() == 0) {
            // Mock Cases
            final String haytham = "haytham";
            final String vasco = "vasco";
            final String jose = "jose";
            // Get Customer From customer-rest service
            CustomerDTO haythamCustomer = this.customerClient.getCustomer(haytham).getBody();
            CustomerDTO vascoCustomer = this.customerClient.getCustomer(vasco).getBody();
            CustomerDTO joseCustomer = this.customerClient.getCustomer(jose).getBody();
            CaseBO case1 = new CaseBO("Patient presents with Flank Pain. The patient is a 51-year-old female, no significant past medical history, presents to the emergency department with left-sided flank pain ongoing ×1 month now with abdominal pain. The pain is intermittent, but has been worsening. She reports new onset nausea, vomiting, diarrhea for the last 2 days. She reports multiple episodes of nonbloody emesis starting yesterday. She has also had multiple episodes of nonbloody diarrhea. She has gone to see her primary care doctor twice since symptoms began. She was found to have mildly elevated creatinine and was referred to a nephrologist. However, the nephrologist is not willing to see her until . The patient feels she cannot wait that long especially in light of these new symptoms. She then followed up with her primary care doctor again and he prescribed Zofran and loperamide but offered her no other solutions. The pain has since increased as well. She denies any fevers, chills. She denies urinary symptoms including burning with urination, frequency, hematuria.", null, null, false, null);
            CaseBO case2 = new CaseBO("Patient  is an 45 year old  female.    Chief Complaint:  Problem    HPI  states that about one month ago she woke up with redness and swelling to her left eye.  She went to see an ophthalmologist who prescribed her naphazoline.  She states that this relieves the redness only temporarily.  She also states that this morning she awoke with more crusting to the left eye.  The eye is not particularly itchy, but seems more irritated today.  She has not had any sick contacts.          Review of Systems   Constitutional: Negative for fever.   Eyes: Positive for discharge and redness. Negative for blurred vision, double vision and photophobia.   Skin: Negative for itching.   Neurological: Positive for headaches.         Objective:     BP 100/69  -Strict ER precautions reviewed with patient should symptoms persist or worsen (specific signs reviewed verbally).  Good communication established and plan agreed upon by patient.", null, null, false, null);
            CaseBO case3 = new CaseBO("Patient  is an 42 year old  male.    Chief Complaint: Establish Care and Physical    HPI      Hemorrhoids  Bothersome  Comes and goes  Especially with sedentary life style  Recently worse  Couple nights where almost wakes patient up  Gets intermittently constipated  High fiber diet    Patient Active Problem    Diagnoses Code\n" +
                    " -  Hemorrhoids 455.6E       No outpatient prescriptions have been marked as taking for the  encounter (Office Visit) with ,  C.     Allergies   Allergen Reactions\n" +
                    " -  Pcn (Penicillins)\n" +
                    " -  Morphine        No past medical history on file.  Past Surgical History   Procedure Date\n" +
                    " -  Hx knee surgery      Arthroscopy age 15 for torn meniscus       Family History   Problem Relation  of Onset\n" +
                    " -  Cancer Mother      Breast\n" +
                    " -  Hypertension Mother\n" +
                    " -  Hypertension Father      History   Substance Use Topics\n" +
                    " -  Smoking status: Never Smoker\n" +
                    " -  Smokeless tobacco: Not on file\n", null, null, false, null);
            this.caseRepository.save(case1);
            this.caseRepository.save(case2);
            this.caseRepository.save(case3);
            // Set Customer
            this.caseRepository.updateCaseCustomer(case1.getId(), haythamCustomer.getId());
            this.caseRepository.updateCaseCustomer(case2.getId(), vascoCustomer.getId());
            this.caseRepository.updateCaseCustomer(case3.getId(), joseCustomer.getId());
        }
    }
}
