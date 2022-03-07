<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\ReservationEv;
use App\Form\EventFormType;
use App\Form\ReservationFormType;
use App\Repository\EvenementRepository;
use App\Repository\ReservationEvRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Filesystem\Exception\FileNotFoundException;

class EvenementController extends AbstractController
{
    /**
     * @Route("/pathevenement", name="evenement")
     */
    public function index(EvenementRepository $repository,Request $request): Response
    {
        $listEvents=$repository->findAll();
        return $this->render('evenement/index.html.twig', [
            'listEvents' => $listEvents,
        ]);
    }

    /**
     * @Route("/EventDetail/{id}", name="Front_event_show", methods={"GET"})
     */
    public function FrontEventshow(Evenement $evenement): Response
    {
        return $this->render('evenement/DetailEvent.html.twig',[
            'evenement' => $evenement,
        ]);
    }

    /**
     * @Route("/admin/evenement", name="Adminevenement")
     */
    public function BackView(EvenementRepository $repository,Request $request): Response
    {
        $listEvents=$repository->findAll();
        return $this->render('evenement/index_back.html.twig', [
            'listEvents' => $listEvents,
        ]);
    }
    /**
     * @Route("/evenement/add", name="event_add")
     */
    public function add(Request $request){
        $event = new Evenement();
        $form=$this->createForm(EventFormType::class,$event);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $file=$event->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $manager=$this->getDoctrine()->getManager();
            $event->setImage($fileName);
            $manager->persist($event);
            $manager->flush();
            return $this->redirectToRoute("Adminevenement");
        }
        return $this->render('evenement/add.html.twig',[
            'form'=>$form->createView(),
        ]);
    }
    /**
     * @Route("/evenement/edit/{id}", name="event_edit")
     */
    public function edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EventFormType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file=$evenement->getImage();
            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $evenement->setImage($fileName);
            $entityManager->flush();

            return $this->redirectToRoute('Adminevenement', [], Response::HTTP_SEE_OTHER);
        }

        return $this->render('evenement/add.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/evenement/delete/{id}", name="event_delete")
     */
    public function delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $entityManager->remove($evenement);
        $entityManager->flush();
        return $this->redirectToRoute('Adminevenement', [], Response::HTTP_SEE_OTHER);
    }

    /**
     * @Route("reserverEvent/{id}",name="reserver")
     */
    public function reserver(Request $request,Evenement $evenement,UserRepository $userRepo,EntityManagerInterface $manager){

        $user=$userRepo->find(1);
        $reservation=new ReservationEv();
        $reservation->setEvenement($evenement);
        $reservation->setUser($user);
        $form=$this->createForm(ReservationFormType::class,$reservation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            if($reservation->getNbrPlace() > $evenement->getCapacity()){
                return new Response("YOU'VE PASSED THE CAPACITY");
            }
            $manager->persist($reservation);
            $manager->flush();
            $evenement->setCapacity($evenement->getCapacity()-$reservation->getNbrPlace());
            //$manager->persist($evenement);
            $manager->flush();
            return $this->redirectToRoute('evenement');
        }
        return $this->render('evenement/reservation/reserver.html.twig',[
            'form'=>$form->createView(),
            'event'=>$evenement,
            'user'=>$user
        ]);
    }

    /**
     *@Route("/pdf/imen", name="imprimer", methods={"GET"})
     */
    public function pdf(EvenementRepository $evenementRepository): Response
    {

        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        $dompdf = new Dompdf($pdfOptions);

        $html = $this->renderView('evenement/pdf.html.twig', [
            'events' => $evenementRepository->findAll(),
        ]);

        $dompdf->loadHtml($html);


        $dompdf->setPaper('A4', 'portrait');

        $dompdf->render();


        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);

    }
}