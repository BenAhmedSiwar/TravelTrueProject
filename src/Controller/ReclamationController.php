<?php

namespace App\Controller;


use App\Entity\Reclamation;
use App\Repository\ReclamationRepository;
use App\Form\ReclamationType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

class ReclamationController extends AbstractController
{
    /**
     * @Route("/reclamation", name="reclamation")
     */
    public function index(): Response
    {
        return $this->render('reclamation/index.html.twig', [
            'controller_name' => 'ReclamationController',
        ]);
    }

    /**
     * @Route("AfficheReclamation", name="AfficheReclamation")
     * @param ReclamationRepository $repo
     * @return Response
     */
    public function Affiche(ReclamationRepository $repo)
    {

        $reclamation = $repo->findAll();
        return $this->render('reclamation/Affiche.html.twig',
            ['reclamation' => $reclamation]);
    }

    /**
     * @Route("AddReclamation", name="AddReclamation")
     * @return Response
     */

    function Ajouter(Request $request)
    {
        $reclamation = new Reclamation();
        $reclamation->setStatut('Pending');
        $reclamation->setDatereclamation(new \DateTime('now'));
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->add('ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();
            return $this->redirectToRoute('AfficheReclamation');
        }
        return $this->render('reclamation/Add.html.twig', [
            'form' => $form->createView()
        ]);

    }
    /**
     * @Route("deleteReclamation/{id}",name="deleteReclamation")
     */
    function deleteS($id, ReclamationRepository $repo){
        $reclamation=$repo->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('AfficheReclamation');
    }
    /**
     * @Route("UpdateReclamation/{id}",name="UpdateReclamation")
     */
    function Update(ReclamationRepository $repo, $id,Request $request){
        $reclamation=$repo->find($id);
        $form=$this->createForm(ReclamationType::class,$reclamation);
        $form->add('Update', SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('AfficheReclamation');
        }
        return $this->render('reclamation/Update.html.twig',[
            'f'=>$form->createView()]);

    }



}
